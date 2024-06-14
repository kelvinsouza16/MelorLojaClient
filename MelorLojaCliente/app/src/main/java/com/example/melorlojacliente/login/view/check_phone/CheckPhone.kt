package com.example.melorlojacliente.login.view.check_phone

import androidx.annotation.StringRes
import androidx.fragment.app.FragmentActivity
import com.example.melorlojacliente.commom.base.BasePresenter
import com.google.firebase.auth.PhoneAuthCredential

interface CheckPhone {
    interface Presenter : BasePresenter {
        fun sendVerificationCode(phone: String, requireActivity: FragmentActivity)
        fun check(verificationId: String, sms: String)
        fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential, sms: String)
        fun fetchUserRecord(phone: String)
    }

    interface View {
        fun progress(enabled: Boolean)
        fun setVerificationCode(code: String)
        fun displaySmsCodeFailure(@StringRes smsCodeError: Int?)
        fun onUserAuthenticated()
        fun goToMainScreen()
        fun goToRegisterUserDataScreen()
        fun onUserUnauthorized(message: String)
        fun displayMessage(message: String)
    }
}