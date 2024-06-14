package com.example.melorlojacliente.commom.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.melorlojacliente.commom.extensions.config
import com.example.melorlojacliente.databinding.DialogErrorBinding

class ErrorDialog(ctx: Context, private val message: String) : Dialog(ctx) {

    private lateinit var binding: DialogErrorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DialogErrorBinding.inflate(layoutInflater)
        config(binding)
        binding.tvErrorMessage.text = message
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

}