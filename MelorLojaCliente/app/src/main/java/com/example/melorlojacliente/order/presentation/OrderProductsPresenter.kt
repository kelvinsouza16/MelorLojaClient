package com.example.melorlojacliente.order.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.melorlojacliente.order.ckeckout.OrderProducts
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class OrderProductsPresenter(private var view: OrderProducts.View?) : OrderProducts.Presenter, ViewModel() {

    override val isDeliveryFlow = MutableStateFlow(false)

    override val deliveryAddress = MutableStateFlow("Delivery address")

    override fun setDeliveryMethod(isDelivery: Boolean) {
        viewModelScope.launch { isDeliveryFlow.emit(isDelivery) }
    }

    override fun onDestroy() {
        view = null
    }
}