package com.example.melorlojacliente.home.details

import android.content.Context
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.melorlojacliente.R
import com.example.melorlojacliente.commom.base.BaseFragment
import com.example.melorlojacliente.commom.extensions.getFinanceType
import com.example.melorlojacliente.commom.models.ProductWithCount
import com.example.melorlojacliente.databinding.FragmentProductDetailBinding
import com.example.melorlojacliente.home.ProductDetails
import com.example.melorlojacliente.home.presentation.ProductDetailsPresenter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class FragmentProductDetails(val productWithCount: ProductWithCount) :
    BaseFragment<FragmentProductDetailBinding, ProductDetails.Presenter>(
        R.layout.fragment_product_detail,
        FragmentProductDetailBinding::bind
    ),
    ProductDetails.View {

    override lateinit var presenter: ProductDetails.Presenter

    private var productDetailsAttachListener: ProductDetailsAttachListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is ProductDetailsAttachListener) {
            productDetailsAttachListener = context
        }
    }

    override fun setupViews() {
        binding?.productImgBack?.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        presenter.productFlow.onEach { productWithCount ->

            val productData = productWithCount.productData

            binding?.apply {
                productDetailsTxtCategoryName.text = productData.category?.name
                Glide.with(requireContext())
                    .load(productData.imageUri)
                    .placeholder(R.drawable.place)
                    .into(productDetailsImageProduct)
                productDetailsTxtName.text = productData.name
                productDetailsTxtReference.text = productData.reference
                productDetailsTxtSize.text = productData.size
                productDetailsTxtStock.text = productData.stock
                productDetailsTxtDescription.text = productData.description
                productDetailsTxtPrice.text = productData.price?.getFinanceType()

                productDetailsBtnToBasket.apply {
                    Log.d("TEST", productWithCount.count.toString())
                    if (productWithCount.count == 0) {
                        text = resources.getString(R.string.add_to_basket)
                        setTextColor(Color.WHITE)
                        setBackgroundResource(R.drawable.bg_to_basket_btn)
                    } else {
                        setTextColor(Color.BLACK)
                        setBackgroundResource(R.drawable.bg_in_basket_btn)
                        text = resources.getString(R.string.in_basket)
                    }
                    setOnClickListener {
                        if (productWithCount.count > 0) {
                            productDetailsAttachListener?.goToBasketScreen()
                        } else {
                            presenter.addBasket()
                        }
                    }
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        presenter.setProduct(productWithCount)
    }

    override fun setupPresenter() {
        presenter = ProductDetailsPresenter(this)
    }

    override fun onDestroy() {
        binding = null
        presenter.onDestroy()
        super.onDestroy()
    }
}
