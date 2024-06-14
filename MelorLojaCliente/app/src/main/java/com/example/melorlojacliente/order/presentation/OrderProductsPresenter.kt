package com.example.melorlojacliente.order.presentation

import com.example.melorlojacliente.order.ckeckout.OrderProducts

class OrderProductsPresenter(private var view: OrderProducts.View?) : OrderProducts.Presenter {
    override fun onDestroy() {
        view = null
    }
}