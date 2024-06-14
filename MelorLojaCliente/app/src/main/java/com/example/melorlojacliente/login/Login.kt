package com.example.melorlojacliente.login

import androidx.annotation.StringRes
import com.example.melorlojacliente.commom.base.BasePresenter

interface Login {
    interface Presenter : BasePresenter {
        fun validate(phone: String)
    }

    interface View {
        fun progress(enabled: Boolean)
        fun displayPhoneFailure(@StringRes phoneError: Int?)
        fun goToCheckerPhoneScreen(phone: String)
    }
}