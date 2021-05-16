package com.ptit.appsql.order

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ptit.appsql.ModifyActivity
import com.ptit.appsql.R
import com.ptit.appsql.databinding.OrderListFragmentBinding

class OrderListFragment: Fragment() {
    private lateinit var binding: OrderListFragmentBinding
    private lateinit var viewModel: OrderViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = OrderListFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this).get(OrderViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.getAllOrder()
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = OrderAdapter(
            viewLifecycleOwner,
            object : OrderClickListener {
                override fun onClick(order: Order) {
                    val intent = Intent(requireActivity(), ModifyActivity::class.java)
                    intent.putExtra("Order", order.id)
                    startActivity(intent)
                }
            }
        )
        binding.rvOrder.adapter = adapter
        binding.rvOrder.layoutManager = LinearLayoutManager(requireContext())

        binding.buttonAdd.setOnClickListener {
            val intent = Intent(requireActivity(), ModifyActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().menuInflater.inflate(R.menu.main_menu, menu)
        val searchItem: MenuItem? = menu.findItem(R.id.action_search)
        val searchManager = requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.searchOrder(it)
                }
                return true
            }

        })
        return super.onCreateOptionsMenu(menu, inflater)
    }
}