package com.example.melorlojacliente.main

import androidx.lifecycle.LiveData
import com.example.melorlojacliente.commom.base.BasePresenter

interface Main {
    interface Presenter : BasePresenter {
        val loadingLiveData: LiveData<Boolean>
        fun basketFlow()
        fun basketCloseFlow()
    }

    interface View {
        fun displayBasketVisible(double: Double)
    }
}