package com.example.melorlojacliente.home

import com.example.melorlojacliente.commom.models.ProductWithCount

interface HomeAttachListener {
    fun goToBasketScreen()
    fun goToProductDetailsScreen(productWithCount: ProductWithCount)
}