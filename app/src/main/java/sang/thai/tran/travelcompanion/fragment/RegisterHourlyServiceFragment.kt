package sang.thai.tran.travelcompanion.fragment

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import butterknife.OnClick
import kotlinx.android.synthetic.main.fragment_register_hourly_service.*
import kotlinx.android.synthetic.main.layout_back_next.*
import sang.thai.tran.travelcompanion.R
import sang.thai.tran.travelcompanion.activity.BaseActivity
import sang.thai.tran.travelcompanion.activity.MainActivity
import sang.thai.tran.travelcompanion.model.RegisterModel
import sang.thai.tran.travelcompanion.model.Response
import sang.thai.tran.travelcompanion.retrofit.BaseObserver
import sang.thai.tran.travelcompanion.retrofit.HttpRetrofitClientBase
import sang.thai.tran.travelcompanion.utils.AppConstant
import sang.thai.tran.travelcompanion.utils.AppConstant.*
import sang.thai.tran.travelcompanion.utils.AppUtils.openDatePicker
import sang.thai.tran.travelcompanion.utils.AppUtils.openTimePicker
import sang.thai.tran.travelcompanion.utils.ApplicationSingleton
import sang.thai.tran.travelcompanion.utils.DialogUtils
import sang.thai.tran.travelcompanion.utils.Log

class RegisterHourlyServiceFragment : BaseFragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        et_departure_date?.editText?.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                openDepartureDate()
            }
        }
        et_departure_date.setOnClickListener { openDepartureDate() }

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

        tv_register_service_more?.requestFocus()
        tv_register_service_more?.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                registerServiceMore(API_ADDITIONAL_ASSISTANCE)
            }
//            tv_register_service_more?.clearFocus()
        }
        tv_register_service_more.setOnClickListener { registerServiceMore(API_ADDITIONAL_ASSISTANCE) }

        tv_register_service?.requestFocus()
        tv_register_service?.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!isMultiClicked() && hasFocus) {
                registerService()
//                tv_register_service?.clearFocus()
            } else {
//                tv_register_service?.clearFocus()
            }
        }
        tv_register_service.setOnClickListener { registerService() }

        btn_next.setOnClickListener {
            //            openDepartureDate()
            registerApi()
        }

        btn_back.setOnClickListener {
            //            openDepartureDate()
            (activity as BaseActivity).onBackPressed()

        }
    }

    override fun getApiUrl(): String {
        return API_UPDATE_WELL_TRAINED
    }

    override fun layoutId(): Int {
        return R.layout.fragment_register_hourly_service
    }

    @OnClick(R.id.tv_register_service)
    fun registerService() {
        if (activity == null || isMultiClicked()) {
            return
        }
        registerServiceMore(API_SELECTED_ASSISTANCE)
//        showOptionDialog(tv_register_service?, getString(R.string.label_for), activity?.resources.getTextArray(R.array.register_for_list))
//        HttpRetrofitClientBase.getInstance().executeGet(AppConstant.API_SELECTED_ASSISTANCE,
//                ApplicationSingleton.getInstance().token, object : BaseObserver<Response>(true) {
//            override fun onSuccess(result: Response, response: String) {
//                hideProgressDialog()
//                if (activity == null) {
//                    return
//                }
//                if (result.statusCode == AppConstant.SUCCESS_CODE) {
//                    Log.d("Sang", "response: $response")
//                    lstAssistance = result.result?.data?.list!!
//                    result.result?.data?.list?.let { it ->
//                        val listString  = Array(it.size) { "$it" }
//                        for ( i in 0 until it.size) {
//                            listString[i] = it[i].text_VN.toString()
//                        }
//                        activity?.runOnUiThread { showOptionDialog(tv_register_service, getString(R.string.label_register_service_package), listString)
//                    } }
//                } else {
//                    activity?.runOnUiThread { DialogUtils.showAlertDialog(activity, result.message) { dialog, _ -> dialog.dismiss() } }
//                }
//            }
//
//            override fun onFailure(e: Throwable, errorMsg: String) {
//                hideProgressDialog()
//                if (!TextUtils.isEmpty(errorMsg)) {
//                    activity?.runOnUiThread { DialogUtils.showAlertDialog(activity, errorMsg) { dialog, _ -> dialog.dismiss() } }
//                }
//            }
//        })
//
//        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(tv_register_service?.windowToken, 0)
    }

    fun registerServiceMore(url : String) {
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
                    Log.d("Sang", "response: $response")
                    lstAssistance = result.result?.data?.list!!
                    
                    result.result?.data?.list?.let { it ->
                        val listString  = Array(it.size) { "$it" }
                        for ( i in 0 until it.size) {
                            listString[i] = it.get(i).text_VN.toString()
                        }
                        activity?.runOnUiThread { showOptionDialog(tv_register_service_more, getString(R.string.label_register_service_package_additional), listString)
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
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(tv_register_service_more?.windowToken, 0)
    }

    private fun openDepartureDate() {
        if (activity == null || isMultiClicked()) {
            return
        }
        openDatePicker(activity, et_departure_date)
    }

    private fun openFromTime() {
        if (activity == null || isMultiClicked()) {
            return
        }
        openTimePicker(activity, et_from)
    }

    private fun openToTime() {
        if (activity == null || isMultiClicked()) {
            return
        }
        openTimePicker(activity, et_to)
    }

    fun register() {
        if (activity == null) {
            return
        }
        (activity as MainActivity).finishRegister()
    }

    override fun createRegisterFlight(): RegisterModel {
        var registerModel = ApplicationSingleton.getInstance().registerModel
        if (registerModel == null) {
            registerModel = RegisterModel()
        }

        registerModel.id = ApplicationSingleton.getInstance().userInfo.code
        registerModel.contactName = et_full_name?.text
        registerModel.wellTrainedObject = tv_register_service?.text.toString()
        registerModel.departureDateFrom = et_departure_date?.text.toString() + " " + et_departure_time?.text.toString()
        registerModel.pickupPoint = et_pickup_place?.text
        registerModel.visitPlaces = et_visit_place?.text
        registerModel.additionalServices = tv_register_service_more?.text.toString()
        return registerModel
    }

    companion object {

        fun newInstance(): RegisterHourlyServiceFragment {
            return RegisterHourlyServiceFragment()
        }
    }
}
