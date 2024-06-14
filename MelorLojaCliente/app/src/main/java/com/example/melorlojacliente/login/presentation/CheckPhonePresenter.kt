package com.example.melorlojacliente.login.presentation

import androidx.fragment.app.FragmentActivity
import com.example.melorlojacliente.R
import com.example.melorlojacliente.commom.base.RequestCallback
import com.example.melorlojacliente.data.repository.Repository
import com.example.melorlojacliente.login.view.LoginCallback
import com.example.melorlojacliente.login.view.check_phone.CheckPhone
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class CheckPhonePresenter(private var view: CheckPhone.View?, private val repository: Repository) : CheckPhone.Presenter {
    override fun sendVerificationCode(phone: String, requireActivity: FragmentActivity) {
        view?.progress(true)
        val options = PhoneAuthOptions.newBuilder(Firebase.auth).setPhoneNumber(phone)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    view?.setVerificationCode(p0)
                    view?.progress(false)
                }

                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    val code = p0.smsCode
                    if (code != null) {
                        view?.setVerificationCode(code)
                    }
                    view?.progress(false)
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    view?.displayMessage(p0.localizedMessage?.toString() ?: "Erro interno")
                    view?.progress(false)
                }

            })
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override fun check(verificationId: String, sms: String) {
        view?.progress(true)
        if (sms.isNotEmpty()) {
            val credential = PhoneAuthProvider.getCredential(verificationId, sms)
            signInWithPhoneAuthCredential(credential, sms)
        } else {
            view?.displaySmsCodeFailure(R.string.invalid_sms_code)
            view?.progress(false)
        }
    }

    override fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential, sms: String) {
        repository.signInWithPhoneAuthCredential(credential, sms, object : LoginCallback {
            override fun onSuccess() {
                view?.onUserAuthenticated()
            }

            override fun onFailure(message: String) {
                view?.onUserUnauthorized(message)
            }

            override fun onComplete() {
                view?.progress(false)
            }

        })
    }

    override fun fetchUserRecord(phone: String) {
        view?.progress(true)
        repository.fetchUserRecord(phone, object : RequestCallback<Boolean> {
            override fun onSuccess(data: Boolean) {
                if (data) {
                    view?.goToMainScreen()
                } else {
                    view?.goToRegisterUserDataScreen()
                }
            }

            override fun onFailure(message: String?) {
                view?.onUserUnauthorized(message!!)
            }

            override fun onComplete() {
                view?.progress(false)
            }

        })
    }

    override fun onDestroy() {
        view = null
    }
}