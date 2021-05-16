package com.ptit.appsql.student

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("app:student")
fun setStudent(recyclerView: RecyclerView, entries: List<Student>?){
    if(entries == null || recyclerView.adapter == null){
        return
    }
    (recyclerView.adapter as StudentAdapter).submitList(
        entries
    )
}