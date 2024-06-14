package com.example.melorlojacliente.commom.base

import kotlin.String

interface RequestCallback<T> {
    fun onSuccess(data: T)
    fun onFailure(message: String?)
    fun onComplete()
}