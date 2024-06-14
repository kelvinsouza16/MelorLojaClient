package com.example.melorlojacliente.splash.presentation

import com.example.melorlojacliente.splash.Splash
import com.example.melorlojacliente.splash.data.SplashCallback
import com.example.melorlojacliente.splash.data.SplashRepository

class SplashPresenter(private var view: Splash.View?, private val repository: SplashRepository) : Splash.Presenter {
    override fun authenticated() {
        repository.session(object : SplashCallback {
            override fun onSuccess() {
                view?.goToMainScreen()
            }

            override fun onFailure() {
                view?.goToLoginScreen()
            }
        })
    }

    override fun onDestroy() {
        view = null
    }

}