package com.example.melorlojacliente.data.repository

import com.example.melorlojacliente.commom.base.RequestCallback
import com.example.melorlojacliente.commom.models.Category
import com.example.melorlojacliente.commom.models.Product
import com.example.melorlojacliente.data.DataSource
import com.example.melorlojacliente.login.view.LoginCallback
import com.google.firebase.auth.PhoneAuthCredential

class Repository(private var dataSource: DataSource) {
    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential, sms: String, callback: LoginCallback) {
        dataSource.signInWithPhoneAuthCredential(credential, sms, callback)
    }
    fun fetchUserRecord(phone: String, callback: RequestCallback<Boolean>) {
        dataSource.fetchUserRecord(phone, callback)
    }
    fun register(name: String, phone: String, address: String, birthday: String, callback: RequestCallback<String>) {
        dataSource.register(name, phone, address, birthday, callback)
    }
    fun fetchAllProducts(callback: RequestCallback<List<Product>>) {
        dataSource.fetchAllProducts(callback)
    }
    fun fetchAllCategories(callback: RequestCallback<List<Category>>) {
        dataSource.fetchAllCategories(callback)
    }
}