package com.example.melorlojacliente.data

import com.example.melorlojacliente.commom.base.RequestCallback
import com.example.melorlojacliente.commom.models.Category
import com.example.melorlojacliente.commom.models.Product
import com.example.melorlojacliente.login.view.LoginCallback
import com.google.firebase.auth.PhoneAuthCredential

interface DataSource {
    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential, sms: String, callback: LoginCallback)
    fun fetchUserRecord(phone: String, callback: RequestCallback<Boolean>)
    fun register(name: String, phone: String, address: String, birthday: String, callback: RequestCallback<String>)
    fun fetchAllProducts(callback: RequestCallback<List<Product>>)
    fun fetchAllCategories(callback: RequestCallback<List<Category>>)
}