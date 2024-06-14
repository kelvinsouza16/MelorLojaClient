package com.example.melorlojacliente.home.view

import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.bumptech.glide.Glide
import com.example.melorlojacliente.R
import com.example.melorlojacliente.commom.base.BaseAdapter
import com.example.melorlojacliente.commom.extensions.getFinanceType
import com.example.melorlojacliente.commom.models.Product

class ViewHolderProduct(
    viewGroup: ViewGroup,
    onItemClickListener: ((Product?, String?) -> Unit)? = null,
    onLongClickListener: ((String?, String?) -> Unit)? = null
) :
    BaseAdapter.BaseViewHolder<Product>(
        R.layout.item_products_list,
        viewGroup,
        onItemClickListener,
        onLongClickListener
    ) {
    override fun bind(item: Product) {
        Glide.with(itemView.context).load(item.imageUri)
            .into(itemView.findViewById(R.id.item_img_product))
        itemView.findViewById<TextView>(R.id.item_txt_product_name).text = item.name
        itemView.findViewById<TextView>(R.id.item_txt_product_reference).text = item.reference
        itemView.findViewById<TextView>(R.id.item_txt_product_size).text = item.size
        itemView.findViewById<TextView>(R.id.item_txt_product_stock).text = item.stock
        itemView.findViewById<TextView>(R.id.item_txt_product_price).text =
            item.price!!.getFinanceType()

        itemView.findViewById<AppCompatButton>(R.id.item_product_btn_add_to_basket).setOnClickListener {
            
        }

        itemView.setOnClickListener {
            onItemClickListener?.invoke(item, null)
        }

        itemView.setOnLongClickListener {
            onLongClickListener?.invoke(item.uuid, item.name)
            true
        }
    }
}