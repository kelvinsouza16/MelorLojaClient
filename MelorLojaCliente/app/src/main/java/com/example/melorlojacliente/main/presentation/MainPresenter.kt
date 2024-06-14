package com.example.melorlojacliente.main.presentation

import androidx.lifecycle.MediatorLiveData
import com.example.melorlojacliente.commom.utils.Basket
import com.example.melorlojacliente.data.repository.Repository
import com.example.melorlojacliente.main.Main

class MainPresenter(private var view: Main.View?, private val repository: Repository) :
    Main.Presenter {

    override val loadingLiveData = MediatorLiveData<Boolean>()

    override fun basketFlow() {
        loadingLiveData.addSource(Basket.productsListLiveData) {
            var summ = 0.0
            for (i in it) {
                if (i.count > 0) {
                    summ += i.count * i.productData.price!!
                }
            }
            view?.displayBasketVisible(summ)
        }
    }

    override fun basketCloseFlow() {
        loadingLiveData.removeSource(Basket.productsListLiveData)
    }

    override fun onDestroy() {
        view = null
    }
}