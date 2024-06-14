package com.example.melorlojacliente.login.view

interface LoginCallback {
    fun onSuccess()
    fun onFailure(message: String)
    fun onComplete()
}