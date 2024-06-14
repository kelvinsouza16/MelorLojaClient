package com.example.melorlojacliente.login.presentation

import com.example.melorlojacliente.R
import com.example.melorlojacliente.login.Login

class LoginPresenter(private var view: Login.View?) : Login.Presenter {
    override fun validate(phone: String) {
        view?.progress(true)
        if (phone.length >= 16) {
            view?.goToCheckerPhoneScreen(phone)
        } else {
            view?.displayPhoneFailure(R.string.invalid_phone)
            view?.progress(false)
        }
    }

    override fun onDestroy() {
        view = null
    }
}