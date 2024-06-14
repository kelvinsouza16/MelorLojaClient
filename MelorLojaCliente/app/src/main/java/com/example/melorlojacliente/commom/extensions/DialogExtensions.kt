package com.example.melorlojacliente.commom.extensions

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.viewbinding.ViewBinding

fun Dialog.config(viewBinding: ViewBinding) {
    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    setContentView(viewBinding.root)
}