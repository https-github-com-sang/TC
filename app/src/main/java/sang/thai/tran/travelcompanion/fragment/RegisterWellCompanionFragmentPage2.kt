package sang.thai.tran.travelcompanion.fragment

import android.os.Bundle
import android.view.View
import butterknife.OnClick
import kotlinx.android.synthetic.main.fragment_register_flight.*
import kotlinx.android.synthetic.main.fragment_register_flight.email_sign_in_button
import kotlinx.android.synthetic.main.fragment_register_hourly_service.*
import kotlinx.android.synthetic.main.fragment_register_well_companion.*
import kotlinx.android.synthetic.main.fragment_register_well_companion_page_2.*
import kotlinx.android.synthetic.main.fragment_register_well_companion_page_2.btn_back
import kotlinx.android.synthetic.main.fragment_register_well_companion_page_2.btn_next
import sang.thai.tran.travelcompanion.R
import sang.thai.tran.travelcompanion.activity.LoginActivity

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
            //            openDepartureDate()
            (activity as LoginActivity).onBackPressed()
        }
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
        fun newInstance(): RegisterWellCompanionFragmentPage2 {
            return RegisterWellCompanionFragmentPage2()
        }
    }
}
