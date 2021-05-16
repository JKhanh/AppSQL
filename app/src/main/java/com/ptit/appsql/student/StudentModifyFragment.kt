package com.ptit.appsql.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.ptit.appsql.databinding.FragmentStudentModifyBinding

class StudentModifyFragment: Fragment() {
    private lateinit var binding: FragmentStudentModifyBinding
    private lateinit var viewModel: StudentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudentModifyBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this).get(StudentViewModel::class.java)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val id: Long = requireActivity().intent.getLongExtra("Student", 0)
        if(id != 0L){
            viewModel.getStudentById(id)
            viewModel.singleStudent.observe(viewLifecycleOwner) { student ->
                if(student != null) {
                    binding.etName.setText(student.name)
                    if (student.gender)
                        binding.rbMale.isChecked = true
                    else
                        binding.rbFemale.isChecked = true
                    binding.etMark.setText(student.mark.toString())
                }
            }
        }

        binding.btnSave.setOnClickListener {
            val name = binding.etName.text
            val gender = if(binding.rbMale.isChecked || binding.rbFemale.isChecked) binding.rbMale.isChecked
            else null
            val mark = binding.etMark.text

            if(name.isNullOrBlank() || mark.isNullOrBlank() || gender == null){
                Toast.makeText(requireContext(), "Some field cannot be null!!", Toast.LENGTH_LONG).show()
            } else {
                val student = Student(name.toString(), gender, mark.toString().toFloat()).apply {
                    this.id = id
                }
                if(id != 0L){
                    viewModel.updateStudent(student)
                } else {
                    viewModel.saveStudent(student)
                }

                requireActivity().finish()
            }
        }


        binding.btnDelete.setOnClickListener {
            val name = binding.etName.text
            val gender = binding.rbMale.isChecked
            val mark = binding.etMark.text

            if(id == 0L){
                Toast.makeText(requireContext(), "Cannot delete this!", Toast.LENGTH_LONG).show()
            } else{
                val student = Student(name.toString(), gender, mark.toString().toFloat()).apply {
                    this.id = id
                }
                requireActivity().finish()
                viewModel.deleteStudent(student)
            }
        }
    }
}