package com.example.melorlojacliente.commom.utils

import androidx.lifecycle.MutableLiveData
import com.example.melorlojacliente.commom.models.Product
import com.example.melorlojacliente.commom.models.ProductWithCount
import com.google.android.gms.maps.model.LatLng

object Basket {
    var productsListLiveData = MutableLiveData<List<ProductWithCount>>()

    val onTheSelfList = listOf("Ordered", " Confirmed", "Delivered")
    val deliveredList = listOf("Ordered", " Confirmed", "On the way", "Delivered")

    var productsList = ArrayList<ProductWithCount>()

    var locationLiveData = MutableLiveData<Pair<String, LatLng>>()

    fun setList(list: List<Product>) {

        productsList.clear()

        productsList.addAll(list.map {
            ProductWithCount(it, 0)
        })
        productsListLiveData.value = productsList.toMutableList()

    }

    fun addProduct(productWithCount: ProductWithCount) {
        val index = findIndex(productWithCount)
        if (index != -1) {
            val data = productsList[index]
            productsList[index] = data.copy(count = data.count + 1)
            productsListLiveData.value = productsList.toMutableList()
        }
    }

    private fun findIndex(productWithCount: ProductWithCount): Int {
        val data = productsList
        for (i in data.indices) {
            if (data[i].productData.uuid == productWithCount.productData.uuid) {
                return i
            }
        }
        return -1
    }

    fun removeProduct(productWithCount: ProductWithCount) {
        val index = findIndex(productWithCount)
        if (index != -1) {
            productsList[index] = productWithCount.copy(count = productWithCount.count - 1)
            productsListLiveData.value = productsList.toMutableList()
        }
    }


}