package com.ptit.appsql.student

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
import com.ptit.appsql.databinding.StudentListFragmentBinding

class StudentListFragment: Fragment() {
    private lateinit var binding: StudentListFragmentBinding
    private lateinit var viewModel: StudentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = StudentListFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this).get(StudentViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.getStudent()

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = StudentAdapter(this,
            object : StudentOnClickListener {
                override fun onClick(student: Student) {
                    val intent = Intent(requireActivity(), ModifyActivity::class.java)
                    intent.putExtra("Student", student.id)
                    startActivity(intent)
                }
            }
        )
        binding.rvStudent.adapter = adapter
        binding.rvStudent.layoutManager = LinearLayoutManager(requireContext())

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
                    viewModel.searchStudent(it)
                }
                return true
            }

        })
        return super.onCreateOptionsMenu(menu, inflater)
    }
}