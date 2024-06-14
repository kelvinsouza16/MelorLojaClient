package com.example.melorlojacliente.commom.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductWithCount(
    val productData: Product,
    var count: Int = 0
) : Parcelable {
    //fun toProductOrder() = ProductOrder(productId = productData.uuid, count)

    fun toOrderItem() = OrderItemDto(productData.uuid, count)
}
