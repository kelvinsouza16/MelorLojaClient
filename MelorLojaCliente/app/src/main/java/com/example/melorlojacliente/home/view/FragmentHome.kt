package com.example.melorlojacliente.home.view

import android.content.Context
import android.view.View
import com.example.melorlojacliente.R
import com.example.melorlojacliente.commom.base.BaseFragment
import com.example.melorlojacliente.commom.base.DependencyInjector
import com.example.melorlojacliente.commom.extensions.hideMainProgress
import com.example.melorlojacliente.commom.extensions.showErrorDialog
import com.example.melorlojacliente.commom.extensions.showMainProgress
import com.example.melorlojacliente.commom.models.Category
import com.example.melorlojacliente.commom.utils.Basket
import com.example.melorlojacliente.databinding.FragmentHomeBinding
import com.example.melorlojacliente.home.Home
import com.example.melorlojacliente.home.HomeAttachListener
import com.example.melorlojacliente.home.presentation.HomePresenter

class FragmentHome : BaseFragment<FragmentHomeBinding, Home.Presenter>(
    R.layout.fragment_home,
    FragmentHomeBinding::bind
), Home.View {
    override lateinit var presenter: Home.Presenter

    private val categoryAdapter: CategoryAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CategoryAdapter()
    }

    private val productAdapter: ProductsAdapter by lazy(LazyThreadSafetyMode.NONE) {
        ProductsAdapter()
    }

    private var homeAttachListener: HomeAttachListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is HomeAttachListener) {
            homeAttachListener = context
        }
    }

    override fun setupViews() {
        binding?.let {
            with(it) {
                homeProductsRv.adapter = productAdapter
                presenter.fetchAllProducts()

                homeCategoriesRv.adapter = categoryAdapter
                presenter.fetchAllCategories()

                categoryAdapter.setCategoryListener { category ->
                    presenter.categoryItemClicked(category, categoryAdapter.selectedPos)
                }

                productAdapter.setItemBasketClickListener { productWithCount ->
                    presenter.addBasket(productWithCount)
                }

                productAdapter.setOpenBasketClickListener {
                    homeAttachListener?.goToBasketScreen()
                }

                productAdapter.setOpenProductDetailsListener { productWithCount ->
                    homeAttachListener?.goToProductDetailsScreen(productWithCount)
                }
            }
        }
    }

    override fun setupPresenter() {
        presenter = HomePresenter(this, DependencyInjector.repository())
    }

    override fun onDestroy() {
        presenter.onDestroy()
        binding = null
        super.onDestroy()
    }

    override fun progress(enabled: Boolean) {
        if (enabled) showMainProgress() else hideMainProgress()
    }

    override fun displayFullProducts() {
        binding?.productTxtEmpty?.visibility = View.GONE
        binding?.homeProductsRv?.visibility = View.VISIBLE
        Basket.productsListLiveData.observe(viewLifecycleOwner) {
            productAdapter.submitList(it)
        }
        productAdapter.notifyDataSetChanged()
    }

    override fun displayEmptyProducts() {
        binding?.productTxtEmpty?.visibility = View.VISIBLE
        binding?.homeProductsRv?.visibility = View.GONE
    }

    override fun displayProductsFailure(message: String?) {
        showErrorDialog(message!!)
    }

    override fun displayFullCategories(categories: List<Category>) {
        categoryAdapter.submitList(categories)
    }

    override fun displayCategoriesFailure(message: String?) {
        showErrorDialog(message!!)
    }

}