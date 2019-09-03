package sang.thai.tran.travelcompanion.fragment

import android.os.Bundle
import butterknife.OnClick
import kotlinx.android.synthetic.main.layout_back_next.*
import sang.thai.tran.travelcompanion.R
import sang.thai.tran.travelcompanion.activity.BaseActivity

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
