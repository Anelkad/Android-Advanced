package com.example.todoapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.ItemToDoBinding

class ToDoListAdapter(
    private val onToDoItemClicked: (ToDoItem) -> Unit,
    private val deleteItem: (Int) -> Unit
) : ListAdapter<ToDoItem, ToDoListAdapter.ViewHolder>(ToDoListDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemToDoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemToDoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            with(binding){
                root.setOnClickListener {
                    onToDoItemClicked.invoke(getItem(adapterPosition))
                }
                tvTitle.setOnClickListener {
                    changeStatus(adapterPosition)
                }
                ibDelete.setOnClickListener {
                    deleteItem.invoke(adapterPosition)
                }
                checkbox.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        getItem(adapterPosition).status = ToDoItem.Status.COMPLETED
                        binding.tvTitle.setTextColor(Color.GRAY)
                    } else {
                        getItem(adapterPosition).status = ToDoItem.Status.PENDING
                        binding.tvTitle.setTextColor(Color.BLACK)
                    }
                }
            }
        }

        fun bind(toDoItem: ToDoItem) {
            when (toDoItem.status) {
                ToDoItem.Status.IN_PROGRESS -> binding.tvTitle.setTextColor(Color.GREEN)
                ToDoItem.Status.COMPLETED -> {
                    binding.tvTitle.setTextColor(Color.GRAY)
                    binding.checkbox.isChecked = true
                }
                ToDoItem.Status.PENDING -> binding.tvTitle.setTextColor(Color.BLACK)
            }
            binding.tvTitle.text = toDoItem.title
            binding.checkbox.isChecked = toDoItem.status == ToDoItem.Status.COMPLETED
        }

        private fun changeStatus(position: Int) {
            val status = when (getItem(position).status) {
                ToDoItem.Status.IN_PROGRESS -> ToDoItem.Status.COMPLETED
                ToDoItem.Status.PENDING -> ToDoItem.Status.IN_PROGRESS
                else -> ToDoItem.Status.COMPLETED
            }
            getItem(position).status = status
            notifyItemChanged(position)
        }
    }
}