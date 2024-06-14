package com.example.melorlojacliente.splash

import com.example.melorlojacliente.commom.base.BasePresenter
import com.example.melorlojacliente.commom.base.BaseView

interface Splash {
    interface Presenter : BasePresenter {
        fun authenticated()
    }

    interface View : BaseView<Presenter> {
        fun goToMainScreen()
        fun goToLoginScreen()
    }
}