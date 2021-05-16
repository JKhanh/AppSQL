package com.ptit.appsql.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ptit.appsql.R
import com.ptit.appsql.databinding.ItemOrderBinding
import java.util.concurrent.Executors

class OrderAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val onClick: OrderClickListener
): ListAdapter<Order, OrderAdapter.OrderViewHolder>(
    AsyncDifferConfig.Builder(
        OrderDiffCallback()
    )
        .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor()).build()
)  {
    class OrderViewHolder(
        private val binding: ItemOrderBinding,
        private val lifecycleOwner: LifecycleOwner,
        private val onClick: OrderClickListener
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(order: Order){
            binding.order = order
            binding.clickListener = onClick
            binding.lifecycleOwner = lifecycleOwner
            binding.executePendingBindings()

            binding.textRating.text = if(order.ratingOrder <= 1){
                binding.root.resources.getString(R.string.star_no, order.ratingOrder)
            } else {
                binding.root.resources.getString(R.string.star_have, order.ratingOrder)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemOrderBinding.inflate(layoutInflater, parent, false)
        return OrderViewHolder(binding, lifecycleOwner, onClick)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = getItem(position)
        holder.bind(order)
    }
}

class OrderDiffCallback: DiffUtil.ItemCallback<Order>(){
    override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem == newItem
    }
}