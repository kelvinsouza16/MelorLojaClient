package com.example.melorlojacliente.register.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.melorlojacliente.R
import com.example.melorlojacliente.commom.dialogs.ProgressDialog
import com.example.melorlojacliente.commom.extensions.replaceFragment
import com.example.melorlojacliente.commom.extensions.statusBarConfigVersion
import com.example.melorlojacliente.databinding.ActivityRegisterBinding
import com.example.melorlojacliente.login.view.FragmentLogin
import com.example.melorlojacliente.main.view.MainActivity
import com.example.melorlojacliente.register.RegisterAttachListener

class RegisterActivity : AppCompatActivity(), RegisterAttachListener {

    private lateinit var binding: ActivityRegisterBinding

    private lateinit var dialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        statusBarConfigVersion()

        val phone =  intent.getStringExtra("phone").toString()

        dialog = ProgressDialog(this)

        val fragment = FragmentRegister(phone)
        replaceFragment(R.id.register_fragment, fragment)
    }

    fun showProgress() {
        dialog.show()
    }

    fun hideProgress() {
        dialog.cancel()
    }

    override fun goToMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}