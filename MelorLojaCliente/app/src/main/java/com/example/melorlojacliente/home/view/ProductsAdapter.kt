package com.example.melorlojacliente.home.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.melorlojacliente.R
import com.example.melorlojacliente.commom.extensions.getFinanceType
import com.example.melorlojacliente.commom.extensions.inflate
import com.example.melorlojacliente.commom.models.ProductWithCount
import com.example.melorlojacliente.databinding.ItemProductsListBinding

class ProductsAdapter : ListAdapter<ProductWithCount, ProductsAdapter.ViewHolder>(itemCallback) {

    private var itemBasketClickListener: ((ProductWithCount) -> Unit)? = null

    fun setItemBasketClickListener(block: (ProductWithCount) -> Unit) {
        itemBasketClickListener = block
    }

    private var openBasketScreenListener: (() -> Unit)? = null

    fun setOpenBasketClickListener(block: () -> Unit) {
        openBasketScreenListener = block
    }

    private var openProductDetailListener: ((ProductWithCount) -> Unit)? = null

    fun setOpenProductDetailsListener(block: (ProductWithCount) -> Unit) {
        openProductDetailListener = block
    }

    inner class ViewHolder(private val binding: ItemProductsListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                itemProductBtnAddToBasket.setOnClickListener {
                    val data = getItem(adapterPosition)
                    if (data.count > 0) {
                        openBasketScreenListener?.invoke()
                    } else
                        itemBasketClickListener?.invoke(data)
                }
                root.setOnClickListener {
                    openProductDetailListener?.invoke(getItem(adapterPosition))
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun onBind() {
            val data = getItem(adapterPosition)
            binding.apply {
                itemTxtProductName.text = data.productData.name
                itemTxtProductName.setSingleLine()
                itemTxtProductReference.text = data.productData.reference
                itemTxtProductSize.text = data.productData.size
                itemTxtProductStock.text = data.productData.stock
                itemTxtProductPrice.text = data.productData.price!!.getFinanceType()
                itemProductBtnAddToBasket.apply {
                    if (data.count == 0) {
                        text = resources.getString(R.string.add_to_basket)
                        setBackgroundResource(R.drawable.bg_to_basket_btn)
                        setTextColor(Color.parseColor("#ffffff"))
                    } else {
                        setTextColor(Color.parseColor("#000000"))
                        setBackgroundResource(R.drawable.bg_in_basket_btn)
                        text = resources.getString(R.string.in_basket)
                    }
                }
                Glide.with(binding.itemImgProduct.context)
                    .load(data.productData.imageUri)
                    .placeholder(R.drawable.place)
                    .into(binding.itemImgProduct)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemProductsListBinding.bind(parent.inflate(R.layout.item_products_list))
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.onBind()
}


val itemCallback = object : DiffUtil.ItemCallback<ProductWithCount>() {
    override fun areItemsTheSame(oldItem: ProductWithCount, newItem: ProductWithCount) =
        oldItem == newItem

    override fun areContentsTheSame(
        oldItem: ProductWithCount,
        newItem: ProductWithCount
    ): Boolean =
        oldItem.count == newItem.count && oldItem.productData.uuid == newItem.productData.uuid
}

