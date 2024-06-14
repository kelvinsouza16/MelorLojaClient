package com.example.melorlojacliente.home

import com.example.melorlojacliente.commom.base.BasePresenter
import com.example.melorlojacliente.commom.models.ProductWithCount
import kotlinx.coroutines.flow.SharedFlow

interface ProductDetails {
    interface Presenter : BasePresenter {
        val productFlow: SharedFlow<ProductWithCount>
        fun setProduct(productWithCount: ProductWithCount)
        fun addBasket()
    }

    interface View {

    }
}
