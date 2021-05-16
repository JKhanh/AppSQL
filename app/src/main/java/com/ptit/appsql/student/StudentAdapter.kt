package com.ptit.appsql.student

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ptit.appsql.databinding.ItemStudentBinding
import java.util.concurrent.Executors

class StudentAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val onClickListener: StudentOnClickListener
): ListAdapter<Student, StudentAdapter.StudentViewHolder>(
    AsyncDifferConfig.Builder(
        StudentDiffCallback()
    )
        .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor()).build()
) {
    class StudentViewHolder(
        private val binding: ItemStudentBinding,
        private val lifecycleOwner: LifecycleOwner,
        private val onClickListener: StudentOnClickListener
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(student: Student){
            binding.student = student
            binding.onClickListener = onClickListener
            binding.lifecycleOwner = lifecycleOwner
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemStudentBinding.inflate(layoutInflater, parent, false)

        return StudentViewHolder(binding, lifecycleOwner, onClickListener)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = getItem(position)
        holder.bind(student)
    }
}

class StudentDiffCallback: DiffUtil.ItemCallback<Student>(){
    override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean {
        return oldItem == newItem
    }
}