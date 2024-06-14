package com.example.melorlojacliente.login.view.check_phone

import android.content.Context
import com.example.melorlojacliente.R
import com.example.melorlojacliente.commom.base.BaseFragment
import com.example.melorlojacliente.commom.base.DependencyInjector
import com.example.melorlojacliente.commom.extensions.hideLoginProgress
import com.example.melorlojacliente.commom.extensions.showErrorDialog
import com.example.melorlojacliente.commom.extensions.showLoginProgress
import com.example.melorlojacliente.commom.extensions.toast
import com.example.melorlojacliente.databinding.FragmentCheckPhoneBinding
import com.example.melorlojacliente.login.presentation.CheckPhonePresenter
import com.example.melorlojacliente.login.view.LoginAttachListener

class FragmentCheckPhone(private val phone: String) :
    BaseFragment<FragmentCheckPhoneBinding, CheckPhone.Presenter>(
        R.layout.fragment_check_phone,
        FragmentCheckPhoneBinding::bind
    ), CheckPhone.View {

    private var verificationId: String? = null

    override lateinit var presenter: CheckPhone.Presenter

    private var checkPhoneAttachListener: LoginAttachListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is LoginAttachListener) {
            checkPhoneAttachListener = context
        }
    }

    override fun setupViews() {

        binding?.let {
            with(it) {
                checkerPhoneImgBack.setOnClickListener {
                    activity?.supportFragmentManager?.popBackStack()
                }

                presenter.sendVerificationCode(phone, requireActivity())

                checkerSmsCodeBtnNext.setOnClickListener {
                    val sms = checkerPhoneSmsChecker.enteredCode
                    if (verificationId != null) {
                        presenter.check(verificationId!!, sms)
                    } else {
                        showErrorDialog("Algo deu errado, tente novamente")
                    }
                }
            }
        }

    }

    override fun setupPresenter() {
        presenter = CheckPhonePresenter(this, DependencyInjector.repository())
    }

    override fun onDestroy() {
        presenter.onDestroy()
        binding = null
        super.onDestroy()
    }

    override fun progress(enabled: Boolean) {
        if (enabled) showLoginProgress() else hideLoginProgress()
    }

    override fun setVerificationCode(code: String) {
        verificationId = code
    }

    override fun displaySmsCodeFailure(smsCodeError: Int?) {
        showErrorDialog(smsCodeError?.let { getString(it) }!!)
    }

    override fun onUserAuthenticated() {
        presenter.fetchUserRecord(phone)
    }

    override fun goToMainScreen() {
        checkPhoneAttachListener?.goToMainScreen()
    }

    override fun goToRegisterUserDataScreen() {
        checkPhoneAttachListener?.goToRegisterUserDataScreen(phone)
    }

    override fun onUserUnauthorized(message: String) {
        showErrorDialog(message)
    }

    override fun displayMessage(message: String) {
        showErrorDialog(message)
    }
}