package com.example.melorlojacliente.main.view

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import com.example.melorlojacliente.R
import com.example.melorlojacliente.commom.base.BaseFragment
import com.example.melorlojacliente.commom.base.DependencyInjector
import com.example.melorlojacliente.commom.extensions.getFinanceType
import com.example.melorlojacliente.commom.extensions.gone
import com.example.melorlojacliente.commom.extensions.hideMainProgress
import com.example.melorlojacliente.commom.extensions.showMainProgress
import com.example.melorlojacliente.commom.extensions.visible
import com.example.melorlojacliente.databinding.FragmentMainBinding
import com.example.melorlojacliente.home.HomeAttachListener
import com.example.melorlojacliente.main.Main
import com.example.melorlojacliente.main.MainAttachListener
import com.example.melorlojacliente.main.presentation.MainPresenter

class FragmentMain : BaseFragment<FragmentMainBinding, Main.Presenter>(
    R.layout.fragment_main,
    FragmentMainBinding::bind
), Main.View {

    override lateinit var presenter: Main.Presenter

    private var oldSum = 0.0

    private lateinit var valueAnimator: ValueAnimator

    private var mainAttachListener: MainAttachListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is MainAttachListener) {
            mainAttachListener = context
        }
    }

    override fun setupViews() {
        binding?.let { binding ->
            with(binding) {
                mainPager.adapter = MainPagerAdapter(requireActivity())
                mainPager.isUserInputEnabled = false

                mainBottomNav.setOnItemSelectedListener {
                    val page = when (it.itemId) {
                        R.id.menu_home -> {
                            0
                        }

                        R.id.menu_orders -> {
                            1
                        }

                        else -> {
                            2
                        }
                    }
                    mainPager.setCurrentItem(page, true)
                    true
                }

                presenter.loadingLiveData.observe(viewLifecycleOwner) {
                    if (it)
                        showMainProgress()
                    else
                        hideMainProgress()
                }

                presenter.basketCloseFlow()
                presenter.basketFlow()

                mainContainerBasket.setOnClickListener {
                    mainAttachListener?.goToBasketScreen()
                }
            }
        }

    }

    override fun setupPresenter() {
        presenter = MainPresenter(this, DependencyInjector.repository())
    }

    override fun onDestroy() {
        if (valueAnimator.isRunning)
            valueAnimator.cancel()
        super.onDestroy()
    }

    @SuppressLint("Recycle")
    private fun tvPriceAnimator(value: Float) {
        valueAnimator = ValueAnimator.ofFloat(oldSum.toFloat(), value).apply {
            addUpdateListener {
                binding?.mainTxtProductsPrice?.text =
                    (it.animatedValue as Float).toDouble().getFinanceType()
            }
            duration = 1000L
            start()
        }
        oldSum = value.toDouble()
    }

    override fun displayBasketVisible(double: Double) {
        if (double == 0.0) {
            binding?.mainContainerBasket?.gone()
        } else {
            binding?.mainContainerBasket?.visible()
        }
        tvPriceAnimator(double.toFloat())
    }

}