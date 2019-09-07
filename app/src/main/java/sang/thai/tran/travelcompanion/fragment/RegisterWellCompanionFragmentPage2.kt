package sang.thai.tran.travelcompanion.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import butterknife.OnClick
import kotlinx.android.synthetic.main.fragment_register_hourly_service.*
import kotlinx.android.synthetic.main.fragment_register_well_companion.*
import kotlinx.android.synthetic.main.fragment_register_well_companion_page_2.*
import kotlinx.android.synthetic.main.fragment_register_well_companion_page_2.et_from
import kotlinx.android.synthetic.main.fragment_register_well_companion_page_2.et_to
import kotlinx.android.synthetic.main.layout_back_next.*
import sang.thai.tran.travelcompanion.R
import sang.thai.tran.travelcompanion.interfaces.ResultMultiChoiceDialog
import sang.thai.tran.travelcompanion.model.Response
import sang.thai.tran.travelcompanion.retrofit.BaseObserver
import sang.thai.tran.travelcompanion.retrofit.HttpRetrofitClientBase
import sang.thai.tran.travelcompanion.utils.AppConstant
import sang.thai.tran.travelcompanion.utils.AppConstant.API_UPDATE_PROFESSIONAL_INFO
import sang.thai.tran.travelcompanion.utils.ApplicationSingleton
import sang.thai.tran.travelcompanion.utils.DialogUtils

class RegisterWellCompanionFragmentPage2 : BaseFragment() {

    override fun layoutId(): Int {
        return R.layout.fragment_register_well_companion_page_2
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btn_next.setOnClickListener {
            openDepartureDate()
        }

        btn_back.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        et_from?.editText?.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                openFromTime()
            }
        }
        et_from.setOnClickListener { openFromTime() }

        et_to?.editText?.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                openToTime()
            }
        }
        et_to.setOnClickListener { openToTime() }

        setOnClickAndShowDialog(tv_register_for, ApplicationSingleton.getInstance().data.helperSubjectQualificationList!!,
                object : ResultMultiChoiceDialog {
                    override fun getListSelectedItem(list: List<String>) {
                        ApplicationSingleton.getInstance().professionalRecordsInfoModel?.subject_Qualification_List = list
                    }
                })
        setOnClickAndShowDialog(tv_register_free_time, ApplicationSingleton.getInstance().data.timesTypeQualificationList!!,
                object : ResultMultiChoiceDialog {
                    override fun getListSelectedItem(list: List<String>) {
                        ApplicationSingleton.getInstance().professionalRecordsInfoModel?.times_Type_Qualification = list[0]
                    }
                })
        setOnClickAndShowDialog(tv_register_support_place, ApplicationSingleton.getInstance().data.districts!!,
                object : ResultMultiChoiceDialog {
                    override fun getListSelectedItem(list: List<String>) {
                        ApplicationSingleton.getInstance().professionalRecordsInfoModel?.places_Qualification_List = list
                    }
                })
    }

    private fun registerObject() {
    }

    @OnClick(R.id.email_sign_in_button)
    internal fun openDepartureDate() {
        if (activity == null || ApplicationSingleton.getInstance().professionalRecordsInfoModel == null) {
            return
        }
        HttpRetrofitClientBase.getInstance().executeProfessionalRecord(API_UPDATE_PROFESSIONAL_INFO,
                ApplicationSingleton.getInstance().token, ApplicationSingleton.getInstance().professionalRecordsInfoModel, object : BaseObserver<Response>(true) {
            override fun onSuccess(result: Response, response: String) {
                hideProgressDialog()
                if (activity == null) {
                    return
                }
                if (result.statusCode == AppConstant.SUCCESS_CODE) {
//                    activity?.supportFragmentManager?.popBackStack()
//                    activity?.supportFragmentManager?.popBackStack()
                    ApplicationSingleton.getInstance().professionalRecordsInfoModel = null
                    activity!!.runOnUiThread { DialogUtils.showAlertDialog(activity, result.message) { dialog, which ->
                        run {
                            dialog.dismiss()
                            while (fragmentManager?.backStackEntryCount!! > 1) {
                                fragmentManager?.popBackStackImmediate()
                            }

                            activity?.onBackPressed()
//                            activity?.supportFragmentManager?.popBackStack()
//                            activity?.supportFragmentManager?.popBackStack()
                        }
                    } }
                } else {
                    activity?.runOnUiThread { DialogUtils.showAlertDialog(activity, result.message) { dialog, _ -> dialog.dismiss() } }
                }
            }

            override fun onFailure(e: Throwable, errorMsg: String) {
                hideProgressDialog()
                if (!TextUtils.isEmpty(errorMsg)) {
                    activity?.runOnUiThread { DialogUtils.showAlertDialog(activity, errorMsg) { dialog, _ -> dialog.dismiss() } }
                }
            }
        })
    }

    companion object {
        fun newInstance(): RegisterWellCompanionFragmentPage2 {
            return RegisterWellCompanionFragmentPage2()
        }
    }
}
