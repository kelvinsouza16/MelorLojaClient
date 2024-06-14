package com.example.melorlojacliente.basket.view

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.melorlojacliente.R
import com.example.melorlojacliente.commom.extensions.getFinanceType
import com.example.melorlojacliente.commom.extensions.inflate
import com.example.melorlojacliente.commom.models.ProductWithCount
import com.example.melorlojacliente.databinding.ItemBasketProductsListBinding
import com.example.melorlojacliente.home.view.itemCallback

class BasketAdapter : ListAdapter<ProductWithCount, BasketAdapter.ViewHolder>(itemCallback) {

    private var itemRemovedListener: ((ProductWithCount) -> Unit)? = null
    private var itemDeleteListener: ((ProductWithCount) -> Unit)? = null
    private var itemAddListener: ((ProductWithCount) -> Unit)? = null


    fun setItemRemovedListener(block: (ProductWithCount) -> Unit) {
        itemRemovedListener = block
    }

    fun setItemDeleteListener(block: (ProductWithCount) -> Unit) {
        itemDeleteListener = block
    }

    fun setItemAddListener(block: (ProductWithCount) -> Unit) {
        itemAddListener = block
    }

    inner class ViewHolder(private val binding: ItemBasketProductsListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                itemBasketImgAddProduct.setOnClickListener {
                    itemAddListener?.invoke(getItem(adapterPosition))
                }
                itemBasketImgMinusProduct.setOnClickListener {
                    itemRemovedListener?.invoke(getItem(adapterPosition))
                }
                itemBasketImgProductDelete.setOnClickListener {
                    itemDeleteListener?.invoke(getItem(adapterPosition))
                }
            }
        }

        fun onBind() {
            binding.apply {
                val data = getItem(adapterPosition)

                Glide.with(binding.itemBasketImgProduct.context)
                    .load(data.productData.imageUri)
                    .placeholder(R.drawable.place)
                    .into(binding.itemBasketImgProduct)

                binding.itemBasketTxtProductName.text = data.productData.name

                binding.itemBasketTxtProductPrice.text = data.productData.price!!.getFinanceType()

                binding.itemBasketTxtProductCount.text = data.count.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemBasketProductsListBinding.bind(parent.inflate(R.layout.item_basket_products_list))
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.onBind()
}