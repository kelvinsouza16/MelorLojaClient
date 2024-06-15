package com.example.melorlojacliente.order.ckeckout

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.lifecycleScope
import com.example.melorlojacliente.R
import com.example.melorlojacliente.commom.base.BaseFragment
import com.example.melorlojacliente.commom.enums.OrderType
import com.example.melorlojacliente.commom.extensions.getFinanceType
import com.example.melorlojacliente.commom.extensions.gone
import com.example.melorlojacliente.commom.extensions.inVisible
import com.example.melorlojacliente.commom.extensions.visible
import com.example.melorlojacliente.commom.models.ProductWithCount
import com.example.melorlojacliente.commom.utils.Basket
import com.example.melorlojacliente.databinding.FragmentOrderProductsBinding
import com.example.melorlojacliente.databinding.ItemOrderProductsListBinding
import com.example.melorlojacliente.order.ckeckout.map.MapAttachListener
import com.example.melorlojacliente.order.presentation.OrderProductsPresenter
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class FragmentOrderProducts : BaseFragment<FragmentOrderProductsBinding, OrderProducts.Presenter>(
    R.layout.fragment_order_products,
    FragmentOrderProductsBinding::bind
), OrderProducts.View {
    override lateinit var presenter: OrderProducts.Presenter

    private var orderType: OrderType = OrderType.SIMPLE

    private lateinit var products: List<ProductWithCount>

    private var summ = 0.0

    private var address: LatLng? = null

    private var mapAttachListener: MapAttachListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is MapAttachListener) {
            mapAttachListener = context
        }
    }

    override fun setupViews() {
        binding?.let { fragmentOrderProductsBinding ->
            with(fragmentOrderProductsBinding) {
                orderProductsImgBack.setOnClickListener {
                    activity?.supportFragmentManager?.popBackStack()
                }

                presenter.isDeliveryFlow.onEach {
                    if (it) {
                        orderType = OrderType.DELIVERY
                        orderProductsImgCheckDelivery.visible()
                        orderProductsImgCheckPickUpInStore.inVisible()
                        orderProductsContainerMap.visible()
                    } else {
                        orderType = OrderType.SIMPLE
                        orderProductsImgCheckDelivery.inVisible()
                        orderProductsImgCheckPickUpInStore.visible()
                        orderProductsContainerMap.gone()
                    }
                }.launchIn(viewLifecycleOwner.lifecycleScope)

                orderProductsContainerOnTheWay.setOnClickListener { presenter.setDeliveryMethod(false) }

                orderProductsContainerDelivery.setOnClickListener { presenter.setDeliveryMethod(true) }

                orderProductsContainerMap.setOnClickListener {
                    mapAttachListener?.goToMapScreen()
                }

                Basket.locationLiveData.observe(viewLifecycleOwner) {
                    address = it.second
                    orderProductsTxtMapDeliveryAddress.text = it.first
                }

                Basket.productsListLiveData.observe(viewLifecycleOwner) {
                    loadOrders(it.filter { productWithCount ->
                        productWithCount.count > 0 })
                }

            }
        }
    }

    override fun setupPresenter() {
        presenter = OrderProductsPresenter(this)
    }

    override fun onDestroy() {
        binding = null
        presenter.onDestroy()
        super.onDestroy()
    }

    @SuppressLint("SetTextI18n")
    private fun loadOrders(list: List<ProductWithCount>) {
        products = list
        summ = 0.0
        for (i in list) {
            summ += i.count * i.productData.price!!
            val orderBinding = ItemOrderProductsListBinding.inflate(layoutInflater)
            orderBinding.itemOrderTxtProductNameWithCount.text = "${i.productData.name} ${i.count}x"
            orderBinding.itemOrderTxtProductPrice.text = i.productData.price.getFinanceType()
            binding?.orderProductsContainer?.addView(orderBinding.root)
        }
        binding?.orderProductsTxtOrderAllSum?.text = summ.getFinanceType()
    }
}