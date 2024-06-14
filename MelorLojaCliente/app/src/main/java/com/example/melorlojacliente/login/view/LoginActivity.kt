package com.example.melorlojacliente.login.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.melorlojacliente.R
import com.example.melorlojacliente.commom.dialogs.ProgressDialog
import com.example.melorlojacliente.commom.extensions.replaceFragment
import com.example.melorlojacliente.commom.extensions.statusBarConfigVersion
import com.example.melorlojacliente.databinding.ActivityLoginBinding
import com.example.melorlojacliente.login.view.check_phone.FragmentCheckPhone
import com.example.melorlojacliente.main.view.MainActivity
import com.example.melorlojacliente.register.view.RegisterActivity

class LoginActivity : AppCompatActivity(), LoginAttachListener {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var dialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        statusBarConfigVersion()

        dialog = ProgressDialog(this)

        val fragment = FragmentLogin()
        replaceFragment(R.id.login_fragment, fragment)
    }

    fun showProgress() {
        dialog.show()
    }

    fun hideProgress() {
        dialog.cancel()
    }

    override fun goToCheckerPhoneScreen(phone: String) {
        val fragment = FragmentCheckPhone(phone)
        replaceFragment(R.id.login_fragment, fragment)
    }

    override fun goToMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun goToRegisterUserDataScreen(phone: String) {
        val intent = Intent(this, RegisterActivity::class.java)
        intent.putExtra("phone", phone)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}