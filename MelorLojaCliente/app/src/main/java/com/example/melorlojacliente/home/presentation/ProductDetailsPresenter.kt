package com.example.melorlojacliente.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.melorlojacliente.commom.models.ProductWithCount
import com.example.melorlojacliente.commom.utils.Basket
import com.example.melorlojacliente.home.ProductDetails
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class ProductDetailsPresenter(private var view: ProductDetails.View?) : ProductDetails.Presenter, ViewModel() {

    override val productFlow = MutableSharedFlow<ProductWithCount>()

    private lateinit var productWithCount: ProductWithCount

    override fun setProduct(productWithCount: ProductWithCount) {
        this.productWithCount = productWithCount
        viewModelScope.launch {
            productFlow.emit(productWithCount)
        }
    }

    override fun addBasket() {
        viewModelScope.launch {
            productFlow.emit(productWithCount.copy(count = 1))
        }
        Basket.addProduct(productWithCount)
    }

    override fun onDestroy() {
        view = null
    }
}