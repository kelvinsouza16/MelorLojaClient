package com.example.melorlojacliente.commom.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.melorlojacliente.commom.extensions.config
import com.example.melorlojacliente.databinding.DialogMessageBinding

class MessageDialog(ctx: Context, private val message: String) : Dialog(ctx) {

    private lateinit var binding: DialogMessageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DialogMessageBinding.inflate(layoutInflater)
        config(binding)
        binding.tvMessage.text = message

        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }
}