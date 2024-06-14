package com.example.melorlojacliente.commom.models

data class User(
    val uuid: String? = null,
    val fullName: String? = null,
    val phoneNumber: String? = null,
    val email: String? = null,
    val password: String? = null,
    val fullAddress: String? = null,
    val birthday: String? = null
)
