package com.example.melorlojacliente.commom.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val uuid: String? = null,
    val name: String? = null
) : Parcelable
