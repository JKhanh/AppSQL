package com.ptit.appsql.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.ptit.appsql.databinding.FragmentOrderModifyBinding

class OrderModifyFragment : Fragment() {
    private lateinit var binding: FragmentOrderModifyBinding
    private lateinit var viewModel: OrderViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderModifyBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this).get(OrderViewModel::class.java)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val id: Long = requireActivity().intent.getLongExtra("Order", 0)
        if (id != 0L) {
            viewModel.getOrderById(id)
            viewModel.singleOrder.observe(viewLifecycleOwner) { order ->
                if (order != null) {
                    binding.etName.setText(order.itemName)
                    binding.etDate.setText(order.dateOrder)
                    binding.etPrice.setText(order.price.toString())
                    binding.rating.rating = order.ratingOrder.toFloat()
                }
            }
        }

        binding.btnSave.setOnClickListener {
            val itemName = binding.etName.text
            val date = binding.etDate.text
            val price = binding.etPrice.text
            val rating = binding.rating.rating

            if (itemName.isNullOrBlank() || date.isNullOrBlank() || price.isNullOrBlank()) {
                Toast.makeText(requireContext(), "Some field cannot be null!!", Toast.LENGTH_LONG)
                    .show()
            } else {
                val order = Order(
                    itemName.toString(),
                    date.toString(),
                    price.toString().toFloat(),
                    rating.toInt()
                ).apply {
                    this.id = id
                }
                if (id != 0L) {
                    viewModel.updateOrder(order)
                } else {
                    viewModel.addOrder(order)
                }

                requireActivity().finish()
            }
        }


        binding.btnDelete.setOnClickListener {
            val itemName = binding.etName.text
            val date = binding.etDate.text
            val price = binding.etPrice.text
            val rating = binding.rating.rating

            if (id == 0L) {
                Toast.makeText(requireContext(), "Cannot delete this!", Toast.LENGTH_LONG).show()
            } else {
                val order = Order(
                    itemName.toString(),
                    date.toString(),
                    price.toString().toFloat(),
                    rating.toInt()
                ).apply {
                    this.id = id
                }
                requireActivity().finish()
                viewModel.deleteOrder(order)
            }
        }
    }
}