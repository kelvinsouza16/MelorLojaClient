package com.example.melorlojacliente.commom.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val uuid: String? = null,
    val imageUri: String? = null,
    val category: Category? = null,
    val name: String? = null,
    val reference: String? = null,
    val stock: String? = null,
    val size: String? = null,
    val price: Double? = null,
    val description: String? = null
) : Parcelable
