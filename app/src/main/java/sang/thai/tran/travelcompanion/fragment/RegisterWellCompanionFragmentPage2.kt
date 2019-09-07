package sang.thai.tran.travelcompanion.fragment

import android.os.Bundle
import android.view.View
import butterknife.OnClick
import kotlinx.android.synthetic.main.fragment_register_hourly_service.*
import kotlinx.android.synthetic.main.fragment_register_well_companion.*
import kotlinx.android.synthetic.main.fragment_register_well_companion_page_2.*
import kotlinx.android.synthetic.main.fragment_register_well_companion_page_2.et_from
import kotlinx.android.synthetic.main.fragment_register_well_companion_page_2.et_to
import kotlinx.android.synthetic.main.layout_back_next.*
import sang.thai.tran.travelcompanion.R
import sang.thai.tran.travelcompanion.utils.ApplicationSingleton

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

        setOnClickAndShowDialog(tv_register_for, ApplicationSingleton.getInstance().data.helperSubjectQualificationList!!)
        setOnClickAndShowDialog(tv_register_free_time, ApplicationSingleton.getInstance().data.timesTypeQualificationList!!)
        setOnClickAndShowDialog(tv_register_support_place, ApplicationSingleton.getInstance().data.districts!!)
    }

    private fun registerObject() {
    }

    @OnClick(R.id.email_sign_in_button)
    internal fun openDepartureDate() {
        if (activity == null) {
            return
        }
        activity?.onBackPressed()
    }

    companion object {
        fun newInstance(): RegisterWellCompanionFragmentPage2 {
            return RegisterWellCompanionFragmentPage2()
        }
    }
}
