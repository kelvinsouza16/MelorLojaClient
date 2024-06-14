package com.example.melorlojacliente.splash.data

import com.example.melorlojacliente.splash.data.SplashCallback

interface SplashDataSource {
    fun session(callback: SplashCallback)
}