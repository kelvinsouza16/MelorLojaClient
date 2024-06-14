package com.example.melorlojacliente.order.view

import com.example.melorlojacliente.R
import com.example.melorlojacliente.commom.base.BaseFragment
import com.example.melorlojacliente.commom.base.DependencyInjector
import com.example.melorlojacliente.databinding.FragmentOrdersBinding
import com.example.melorlojacliente.order.Order
import com.example.melorlojacliente.order.presentation.OrderPresenter

class FragmentOrder : BaseFragment<FragmentOrdersBinding, Order.Presenter>(
    R.layout.fragment_orders,
    FragmentOrdersBinding::bind
), Order.View {
    override lateinit var presenter: Order.Presenter

    override fun setupViews() {
        binding?.let {
            with(it) {

            }
        }
    }

    override fun setupPresenter() {
        presenter = OrderPresenter(this, DependencyInjector.repository())
    }

    override fun onDestroy() {
        presenter.onDestroy()
        binding = null
        super.onDestroy()
    }
}