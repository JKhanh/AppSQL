package com.ptit.appsql.order

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("app:order")
fun setOrder(recyclerView: RecyclerView, entries: List<Order>?){
    if(entries == null || recyclerView.adapter == null){
        return
    }
    (recyclerView.adapter as OrderAdapter).submitList(
        entries
    )
}