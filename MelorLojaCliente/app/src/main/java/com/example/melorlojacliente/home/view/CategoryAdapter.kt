package com.example.melorlojacliente.home.view

import android.graphics.Color
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.melorlojacliente.R
import com.example.melorlojacliente.commom.extensions.inflate
import com.example.melorlojacliente.commom.models.Category
import com.example.melorlojacliente.databinding.ItemCategoryListBinding

class CategoryAdapter :
    ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(categoryItemCallback) {

    var selectedPos = -1

    private var categorySelectedListener: ((Category) -> Unit)? = null

    fun setCategoryListener(block: (Category) -> Unit) {
        categorySelectedListener = block
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CategoryViewHolder(
        ItemCategoryListBinding.bind(parent.inflate(R.layout.item_category_list))
    )

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) = holder.onBind()

    inner class CategoryViewHolder(private val binding: ItemCategoryListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.itemCategoryTxtName.setOnClickListener {
                selectedPos = if (selectedPos == adapterPosition) {
                    -1
                } else {
                    notifyItemChanged(selectedPos)
                    adapterPosition
                }
                categorySelectedListener?.invoke(getItem(adapterPosition))
                notifyItemChanged(adapterPosition)
            }
        }

        fun onBind() {
            binding.apply {
                itemCategoryTxtName.apply {
                    if (selectedPos == adapterPosition) {
                        setBackgroundResource(R.drawable.bg_category_item_selected)
                        setTextColor(Color.parseColor("#ffffff"))
                    } else {
                        setBackgroundResource(R.drawable.bg_category_item_unselected)
                        setTextColor(Color.parseColor("#000000"))
                    }
                    text = getItem(adapterPosition).name
                }
            }
        }

    }
}

private val categoryItemCallback = object : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category) = oldItem == newItem

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean =
        oldItem.uuid == newItem.uuid && oldItem.name == newItem.name
}