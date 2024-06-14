package com.example.melorlojacliente.basket

import com.example.melorlojacliente.commom.base.BasePresenter
import com.example.melorlojacliente.commom.models.ProductWithCount

interface BasketView {
    interface Presenter : BasePresenter {
        fun removeProduct(productWithCount: ProductWithCount)
        fun deleteProduct(productWithCount: ProductWithCount)
        fun addProduct(productWithCount: ProductWithCount)
    }

    interface View {

    }
}
