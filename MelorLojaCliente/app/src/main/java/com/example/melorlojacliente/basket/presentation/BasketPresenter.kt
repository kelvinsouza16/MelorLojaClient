package com.example.melorlojacliente.basket.presentation

import com.example.melorlojacliente.basket.BasketView
import com.example.melorlojacliente.commom.models.ProductWithCount
import com.example.melorlojacliente.commom.utils.Basket
import com.example.melorlojacliente.data.repository.Repository

class BasketPresenter(private var view: BasketView.View?, private val repository: Repository) :
    BasketView.Presenter {
    override fun removeProduct(productWithCount: ProductWithCount) {
        Basket.removeProduct(productWithCount)
    }

    override fun deleteProduct(productWithCount: ProductWithCount) {
        Basket.removeProduct(productWithCount.copy(count = 1))
    }

    override fun addProduct(productWithCount: ProductWithCount) {
        Basket.addProduct(productWithCount)
    }


    override fun onDestroy() {
        view = null
    }
}