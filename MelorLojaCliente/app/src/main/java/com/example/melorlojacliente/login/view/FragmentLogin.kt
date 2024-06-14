package com.example.melorlojacliente.login.view

import android.content.Context
import com.example.melorlojacliente.R
import com.example.melorlojacliente.commom.base.BaseFragment
import com.example.melorlojacliente.commom.extensions.hideLoginProgress
import com.example.melorlojacliente.commom.extensions.showLoginProgress
import com.example.melorlojacliente.databinding.FragmentLoginBinding
import com.example.melorlojacliente.login.Login
import com.example.melorlojacliente.login.presentation.LoginPresenter

class FragmentLogin : BaseFragment<FragmentLoginBinding, Login.Presenter>(
    R.layout.fragment_login,
    FragmentLoginBinding::bind
), Login.View {

    override lateinit var presenter: Login.Presenter

    private var loginAttachListener: LoginAttachListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is LoginAttachListener) {
            loginAttachListener = context
        }
    }

    override fun setupViews() {
        binding?.let {
            with(it) {
                loginBtnNext.setOnClickListener {
                    val cc = loginEditCcp.selectedCountryCodeWithPlus
                    val number = loginEditPhone.text.toString().trim()
                    val phoneNumber = "$cc $number"
                    presenter.validate(phoneNumber)
                }
            }
        }
    }

    override fun setupPresenter() {
        presenter = LoginPresenter(this)
    }

    override fun onDestroy() {
        presenter.onDestroy()
        binding = null
        super.onDestroy()
    }

    override fun progress(enabled: Boolean) {
        if (enabled) showLoginProgress() else hideLoginProgress()
    }

    override fun displayPhoneFailure(phoneError: Int?) {
       binding?.loginEditPhoneInput?.error = phoneError?.let { getString(it) }
    }

    override fun goToCheckerPhoneScreen(phone: String) {
        loginAttachListener?.goToCheckerPhoneScreen(phone)
    }
}