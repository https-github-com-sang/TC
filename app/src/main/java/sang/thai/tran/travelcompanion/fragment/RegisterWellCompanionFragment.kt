package sang.thai.tran.travelcompanion.fragment

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.fragment_register_hourly_service.*
import kotlinx.android.synthetic.main.fragment_register_well_companion.*
import kotlinx.android.synthetic.main.layout_back_next.*
import kotlinx.android.synthetic.main.layout_base_medical_certificate.*
import sang.thai.tran.travelcompanion.R
import sang.thai.tran.travelcompanion.activity.MainActivity
import sang.thai.tran.travelcompanion.model.Response
import sang.thai.tran.travelcompanion.retrofit.BaseObserver
import sang.thai.tran.travelcompanion.retrofit.HttpRetrofitClientBase
import sang.thai.tran.travelcompanion.utils.AppConstant
import sang.thai.tran.travelcompanion.utils.AppConstant.API_GET_PROFESSIONAL_RECORD
import sang.thai.tran.travelcompanion.utils.ApplicationSingleton
import sang.thai.tran.travelcompanion.utils.DialogUtils


class RegisterWellCompanionFragment : BaseFragment() {

    override fun layoutId(): Int {
        return R.layout.fragment_register_well_companion
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btn_next.setOnClickListener {
            (activity as MainActivity).replaceFragment(R.id.fl_content, RegisterWellCompanionFragmentPage2())
        }

        btn_back.setOnClickListener {
            (activity as MainActivity).onBackPressed()
        }
        controlMedicalCertificationUI(sw_certification.isChecked)
        getProfessionalRecords(API_GET_PROFESSIONAL_RECORD)
        sw_certification.setOnCheckedChangeListener { _, b ->
            controlMedicalCertificationUI(b)
        }

    }

    //    GET /api/SelectList/getProfessionalRecords
    private fun getProfessionalRecords(url: String) {
        if (activity == null || isMultiClicked()) {
            return
        }
        HttpRetrofitClientBase.getInstance().executeGet(url,
                ApplicationSingleton.getInstance().token, object : BaseObserver<Response>(true) {
            override fun onSuccess(result: Response, response: String) {
                hideProgressDialog()
                if (activity == null) {
                    return
                }
                if (result.statusCode == AppConstant.SUCCESS_CODE) {
                    ApplicationSingleton.getInstance().data = result.result?.data

                    activity?.runOnUiThread {
                        setOnClickAndShowDialog(tv_professional_qualification, ApplicationSingleton.getInstance().data.degreesList!!)
                        setOnClickAndShowDialog(tv_specialized, ApplicationSingleton.getInstance().data.qualificationList!!)
                        setOnClickAndShowDialog(tv_communication_level, ApplicationSingleton.getInstance().data.communicationSkillsList!!)
                    }
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
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(tv_register_service_more?.windowToken, 0)
    }

    private fun controlMedicalCertificationUI(on: Boolean) {
        if (on) {
            et_give_basic_media_cer.visibility = View.VISIBLE
            ll_join_basic_media_cer.visibility = View.GONE
        } else {
            et_give_basic_media_cer.visibility = View.GONE
            ll_join_basic_media_cer.visibility = View.VISIBLE
        }
    }

    internal fun openDepartureDate() {
        if (activity == null) {
            return
        }
        activity!!.onBackPressed()
    }


    companion object {
        fun newInstance(): RegisterWellCompanionFragment {
            return RegisterWellCompanionFragment()
        }
    }

}
