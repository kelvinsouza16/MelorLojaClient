package com.example.melorlojacliente.home

import com.example.melorlojacliente.commom.base.BasePresenter
import com.example.melorlojacliente.commom.models.Category
import com.example.melorlojacliente.commom.models.ProductWithCount

interface Home {
    interface Presenter : BasePresenter {
        fun fetchAllProducts()
        fun categoryItemClicked(category: Category, selectedPos: Int)
        fun fetchAllCategories()
        fun addBasket(productWithCount: ProductWithCount)
    }

    interface View {
        fun progress(enabled: Boolean)
        fun displayFullProducts()
        fun displayEmptyProducts()
        fun displayProductsFailure(message: String?)
        fun displayFullCategories(categories: List<Category>)
        fun displayCategoriesFailure(message: String?)
    }
}