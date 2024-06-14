package com.example.melorlojacliente.basket.view

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.melorlojacliente.R
import com.example.melorlojacliente.basket.BasketAttachListener
import com.example.melorlojacliente.basket.BasketView
import com.example.melorlojacliente.basket.presentation.BasketPresenter
import com.example.melorlojacliente.commom.base.BaseFragment
import com.example.melorlojacliente.commom.base.DependencyInjector
import com.example.melorlojacliente.commom.dialogs.ConfirmDialog
import com.example.melorlojacliente.commom.extensions.getFinanceType
import com.example.melorlojacliente.commom.models.ProductWithCount
import com.example.melorlojacliente.commom.utils.Basket
import com.example.melorlojacliente.databinding.FragmentBasketBinding
import com.example.melorlojacliente.home.details.ProductDetailsAttachListener

class FragmentBasket : BaseFragment<FragmentBasketBinding, BasketView.Presenter>(
    R.layout.fragment_basket,
    FragmentBasketBinding::bind
), BasketView.View {
    override lateinit var presenter: BasketView.Presenter

    private lateinit var animator: ValueAnimator

    private var oldSum = 0.0

    private val adapter: BasketAdapter by lazy(LazyThreadSafetyMode.NONE) {
        BasketAdapter()
    }

    private var basketAttachListener: BasketAttachListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is BasketAttachListener) {
            basketAttachListener = context
        }
    }

    override fun setupViews() {
        binding?.let {
            with(it) {

                basketImgBack.setOnClickListener {
                    activity?.supportFragmentManager?.popBackStack()
                }

                basketProductsRv.adapter = adapter

                basketProductsRv.addItemDecoration(
                    DividerItemDecoration(
                        requireContext(),
                        LinearLayoutManager.VERTICAL
                    )
                )

                adapter.setItemAddListener { productWithCount ->
                    presenter.addProduct(productWithCount)
                }
                adapter.setItemDeleteListener { productWithCount ->
                    presenter.deleteProduct(productWithCount)
                }
                adapter.setItemRemovedListener { productWithCount ->
                    presenter.removeProduct(productWithCount)
                }

                Basket.productsListLiveData.observe(viewLifecycleOwner) { list ->
                    val news = list.filter { productWithCount ->  productWithCount.count > 0 }
                    basketBtnConfirmOrder.isEnabled = news.isNotEmpty()
                    adapter.submitList(news)
                    tvPriceAnimator(news)
                }

                basketBtnConfirmOrder.setOnClickListener {
                    val confirmDialog = ConfirmDialog(requireContext(), "Você realmente deseja confirmar o pedido?")
                    confirmDialog.setConfirmClickListener {
                        basketAttachListener?.goToFragmentOrderProductsScreen()
                    }
                    confirmDialog.show()
                }

            }
        }
    }

    override fun setupPresenter() {
        presenter = BasketPresenter(this, DependencyInjector.repository())
    }

    override fun onDestroy() {
        presenter.onDestroy()
        binding = null
        super.onDestroy()
    }

    @SuppressLint("Recycle")
    private fun tvPriceAnimator(list: List<ProductWithCount>) {
        var sum = 0.0
        list.forEach {
            sum += it.productData.price!! * it.count
        }
        animator = ValueAnimator.ofFloat(oldSum.toFloat(), sum.toFloat()).apply {
            addUpdateListener {
                binding?.basketTxtOrderValue?.text =
                    (it.animatedValue as Float).toDouble().getFinanceType()
            }
            duration = 1000L
            start()
        }
        oldSum = sum
    }
}