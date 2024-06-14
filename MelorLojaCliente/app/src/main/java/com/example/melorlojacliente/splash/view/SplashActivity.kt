package com.example.melorlojacliente.splash.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.melorlojacliente.commom.base.DependencyInjector
import com.example.melorlojacliente.commom.extensions.animationEnd
import com.example.melorlojacliente.databinding.ActivitySplashBinding
import com.example.melorlojacliente.login.view.LoginActivity
import com.example.melorlojacliente.main.view.MainActivity
import com.example.melorlojacliente.splash.Splash
import com.example.melorlojacliente.splash.presentation.SplashPresenter

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity(), Splash.View {

    private lateinit var binding: ActivitySplashBinding

    override lateinit var presenter: Splash.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = SplashPresenter(this, DependencyInjector.splashRepository())

        binding.splashImg.animate().apply {
            setListener(animationEnd {
                presenter.authenticated()
            })
            duration = 1000
            alpha(1.0f)
            start()
        }
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun goToMainScreen() {
        binding.splashImg.animate().apply {
            setListener(animationEnd {
                val intent = Intent(baseContext, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                    overrideActivityTransition(OVERRIDE_TRANSITION_CLOSE, android.R.anim.fade_in, android.R.anim.fade_out)
                } else {
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                }
            })
            duration = 1000
            startDelay = 1000
            alpha(0.0f)
            start()
        }
    }

    override fun goToLoginScreen() {
        binding.splashImg.animate().apply {
            setListener(animationEnd {
                val intent = Intent(baseContext, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                    overrideActivityTransition(OVERRIDE_TRANSITION_CLOSE, android.R.anim.fade_in, android.R.anim.fade_out)
                } else {
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                }
            })
            duration = 1000
            startDelay = 1000
            alpha(0.0f)
            start()
        }
    }

}
