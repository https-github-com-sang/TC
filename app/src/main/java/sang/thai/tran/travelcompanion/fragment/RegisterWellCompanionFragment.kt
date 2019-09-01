package sang.thai.tran.travelcompanion.fragment

import android.os.Bundle
import android.view.View
import butterknife.OnClick
import kotlinx.android.synthetic.main.fragment_register_flight.*
import kotlinx.android.synthetic.main.fragment_register_flight.email_sign_in_button
import kotlinx.android.synthetic.main.fragment_register_hourly_service.*
import kotlinx.android.synthetic.main.fragment_register_well_companion.*
import sang.thai.tran.travelcompanion.R
import sang.thai.tran.travelcompanion.activity.BaseActivity
import sang.thai.tran.travelcompanion.activity.LoginActivity

class RegisterWellCompanionFragment : BaseFragment() {

    override fun layoutId(): Int {
        return R.layout.fragment_register_well_companion
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btn_next.setOnClickListener {
//            openDepartureDate()
            (activity as BaseActivity).replaceFragment(R.id.fl_content, RegisterWellCompanionFragmentPage2())
        }

        btn_back.setOnClickListener {
            //            openDepartureDate()
            (activity as BaseActivity).onBackPressed()
        }

//        tv_register_service_more?.requestFocus()
//        tv_register_service_more?.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
//            if (hasFocus) {
//                registerObject()
//            }
//            tv_register_service_more?.clearFocus()
//        }
//        tv_register_service_more.setOnClickListener { registerObject() }

    }

    private fun registerObject() {
    }

    @OnClick(R.id.email_sign_in_button)
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
