package com.example.melorlojacliente.register

import androidx.annotation.StringRes
import com.example.melorlojacliente.commom.base.BasePresenter

interface Register {
    interface Presenter : BasePresenter {
        fun register(name: String, phone: String, address: String, birthday: String)
    }

    interface View {
        fun progress(enabled: Boolean)
        fun successRegister(message: String)
        fun failureRegister(message: String?)
        fun displayNameFailure(@StringRes nameError: Int?)
        fun displayPhoneFailure(@StringRes phoneError: Int?)
        fun displayAddressFailure(@StringRes addressError: Int?)
    }
}