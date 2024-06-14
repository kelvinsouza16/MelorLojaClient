package com.example.melorlojacliente.commom.models

import android.location.Address
import com.example.melorlojacliente.commom.enums.OrderType

data class OrderDto(
    val allOrderValue: HashSet<OrderItemDto>,
    val comment: String,
    val orderType: OrderType,
    val address: Address? = null,
    val userId: Long = 0
)
