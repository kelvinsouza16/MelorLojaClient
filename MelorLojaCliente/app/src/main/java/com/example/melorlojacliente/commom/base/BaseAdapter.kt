package com.example.melorlojacliente.commom.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

// o tipo U vai ser um data model generico que vai chegar pelo BaseViewHolder e vamos passar para o BaseAdapter
class BaseAdapter<VH : BaseAdapter.BaseViewHolder<U>, U>(private val viewHolderLaunch: (ViewGroup) -> VH) :
    RecyclerView.Adapter<VH>() {

    var items: List<U> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return viewHolderLaunch(parent)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    abstract class BaseViewHolder<U>(
        @LayoutRes layout: Int,
        viewGroup: ViewGroup,
        val onItemClickListener: ((U?, String?) -> Unit)? = null,
        val onLongClickListener: ((String?, String?) -> Unit)? = null
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(viewGroup.context).inflate(layout, viewGroup, false)
    ) {

        abstract fun bind(item: U)

    }
}


