package com.example.melorlojacliente.commom.base

import com.example.melorlojacliente.data.FireDataSource
import com.example.melorlojacliente.data.repository.Repository
import com.example.melorlojacliente.splash.data.FireSplashDataSource
import com.example.melorlojacliente.splash.data.SplashRepository

object DependencyInjector {

    fun splashRepository() : SplashRepository {
        return SplashRepository(FireSplashDataSource())
    }

    /*fun loginRepository() : LoginRepository {
        return LoginRepository(FireLoginDataSource())
    }

    fun mainRepository() : MainRepository {
        return MainRepository(FireMainDataSource())
    }*/

    fun repository() : Repository {
        return Repository(FireDataSource())
    }
}