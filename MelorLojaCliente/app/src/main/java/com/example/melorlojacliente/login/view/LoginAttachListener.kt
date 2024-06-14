package com.example.melorlojacliente.login.view

interface LoginAttachListener {
    fun goToCheckerPhoneScreen(phone: String)
    fun goToMainScreen()
    fun goToRegisterUserDataScreen(phone: String)
}