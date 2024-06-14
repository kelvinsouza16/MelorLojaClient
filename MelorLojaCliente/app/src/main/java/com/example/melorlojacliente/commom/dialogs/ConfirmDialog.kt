package com.example.melorlojacliente.commom.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.melorlojacliente.commom.extensions.config
import com.example.melorlojacliente.databinding.DialogConfirmBinding

class ConfirmDialog(ctx: Context, var message: String?) : Dialog(ctx) {

    private var confirmClickListener: (() -> Unit)? = null

    private lateinit var binding: DialogConfirmBinding

    fun setConfirmClickListener(block: () -> Unit) {
        confirmClickListener = block
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DialogConfirmBinding.inflate(layoutInflater)
        config(viewBinding = binding)
        binding.tvMessage.text = message
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        binding.btnConfirm.setOnClickListener {
            confirmClickListener?.invoke()
            dismiss()
        }
    }

}