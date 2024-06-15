package com.example.melorlojacliente.order.ckeckout

import com.example.melorlojacliente.commom.base.BasePresenter
import kotlinx.coroutines.flow.StateFlow

interface OrderProducts {
    interface Presenter : BasePresenter {
        val isDeliveryFlow: StateFlow<Boolean>

        val deliveryAddress: StateFlow<String>
        fun setDeliveryMethod(isDelivery: Boolean)
    }

    interface View {

    }
}