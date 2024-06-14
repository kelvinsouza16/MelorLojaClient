package com.example.melorlojacliente.home.presentation

import com.example.melorlojacliente.commom.base.RequestCallback
import com.example.melorlojacliente.commom.models.Category
import com.example.melorlojacliente.commom.models.Product
import com.example.melorlojacliente.commom.models.ProductWithCount
import com.example.melorlojacliente.commom.utils.Basket
import com.example.melorlojacliente.data.repository.Repository
import com.example.melorlojacliente.home.Home

class HomePresenter(private var view: Home.View?, private val repository: Repository) :
    Home.Presenter {

    override fun fetchAllProducts() {
        view?.progress(true)
        repository.fetchAllProducts(object : RequestCallback<List<Product>> {
            override fun onSuccess(data: List<Product>) {
                if (data.isNotEmpty()) {
                    Basket.setList(data)
                    view?.displayFullProducts()
                } else {
                    view?.displayEmptyProducts()
                }
            }

            override fun onFailure(message: String?) {
                view?.displayProductsFailure(message)
            }

            override fun onComplete() {
                view?.progress(false)
            }

        })
    }

    override fun categoryItemClicked(category: Category, selectedPos: Int) {
        view?.progress(true)
        if (selectedPos == -1) {
            Basket.productsListLiveData.value = Basket.productsList
            view?.displayFullProducts()
        } else {
            Basket.productsListLiveData.value = Basket.productsList.filter {
                it.productData.category!!.name == category.name
            }

            if (Basket.productsListLiveData.value!!.isNotEmpty()) {
                view?.displayFullProducts()
            } else {
                view?.displayEmptyProducts()
            }
        }
        view?.progress(false)
    }

    override fun fetchAllCategories() {
        view?.progress(true)
        repository.fetchAllCategories(object : RequestCallback<List<Category>> {
            override fun onSuccess(data: List<Category>) {
                view?.displayFullCategories(data)
            }

            override fun onFailure(message: String?) {
                view?.displayCategoriesFailure(message)
            }

            override fun onComplete() {
                view?.progress(false)
            }

        })
    }

    override fun addBasket(productWithCount: ProductWithCount) {
        Basket.addProduct(productWithCount)
    }

    override fun onDestroy() {
        view = null
    }
}