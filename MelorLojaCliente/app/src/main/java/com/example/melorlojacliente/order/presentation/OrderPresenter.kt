package com.example.melorlojacliente.order.presentation

import com.example.melorlojacliente.data.repository.Repository
import com.example.melorlojacliente.order.Order

class OrderPresenter(private var view: Order.View?, private val repository: Repository) : Order.Presenter {
    override fun onDestroy() {
        view = null
    }
}