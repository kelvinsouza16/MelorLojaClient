package com.example.melorlojacliente.main.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.melorlojacliente.R
import com.example.melorlojacliente.basket.BasketAttachListener
import com.example.melorlojacliente.basket.view.FragmentBasket
import com.example.melorlojacliente.commom.dialogs.ProgressDialog
import com.example.melorlojacliente.commom.extensions.replaceFragment
import com.example.melorlojacliente.commom.extensions.statusBarConfigVersion
import com.example.melorlojacliente.commom.models.ProductWithCount
import com.example.melorlojacliente.databinding.ActivityMainBinding
import com.example.melorlojacliente.home.HomeAttachListener
import com.example.melorlojacliente.home.details.FragmentProductDetails
import com.example.melorlojacliente.home.details.ProductDetailsAttachListener
import com.example.melorlojacliente.main.MainAttachListener
import com.example.melorlojacliente.order.ckeckout.FragmentOrderProducts

class MainActivity : AppCompatActivity(), MainAttachListener, HomeAttachListener,
    ProductDetailsAttachListener, BasketAttachListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var dialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        statusBarConfigVersion()

        dialog = ProgressDialog(this)

        val fragment = FragmentMain()
        replaceFragment(R.id.main_fragment, fragment)
    }

    fun showProgress() {
        dialog.show()
    }

    fun hideProgress() {
        dialog.cancel()
    }

    override fun goToBasketScreen() {
        val fragment = FragmentBasket()
        replaceFragment(R.id.main_fragment, fragment)
    }

    override fun goToProductDetailsScreen(productWithCount: ProductWithCount) {
        val fragment = FragmentProductDetails(productWithCount)
        replaceFragment(R.id.main_fragment, fragment)
    }

    override fun goToFragmentOrderProductsScreen() {
        val fragment = FragmentOrderProducts()
        replaceFragment(R.id.main_fragment, fragment)
    }
}