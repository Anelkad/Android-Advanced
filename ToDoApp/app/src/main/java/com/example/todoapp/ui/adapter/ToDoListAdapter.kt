package com.example.todoapp.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.models.Status
import com.example.todoapp.models.ToDoItem
import com.example.todoapp.databinding.ItemToDoBinding

class ToDoListAdapter(
    private val onToDoItemClicked: (ToDoItem) -> Unit,
    private val deleteItem: (Int) -> Unit,
    private val changeStatus: (Int) -> Unit,
    private val changeCheckboxStatus: (Int, Boolean) -> Unit,
    private val swipeItems: (Int, Int) -> Unit
) : ListAdapter<ToDoItem, ToDoListAdapter.ViewHolder>(ToDoListDiffUtilCallback()),
    ItemTouchHelperAdapter {

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

        private val context = binding.root.context

        init {
            with(binding) {
                tvTitle.setOnClickListener {
                    getItem(bindingAdapterPosition).id?.let { it1 -> changeStatus.invoke(it1) }
                }
                ibDelete.setOnClickListener {
                    getItem(bindingAdapterPosition).id?.let { it1 -> deleteItem.invoke(it1) }
                }
                ibEdit.setOnClickListener {
                    onToDoItemClicked.invoke(getItem(bindingAdapterPosition))
                }
                checkbox.setOnCheckedChangeListener { _, isChecked ->
                    getItem(bindingAdapterPosition).id?.let {
                        changeCheckboxStatus.invoke(it, isChecked)
                    }
                }
            }
        }

        fun bind(toDoItem: ToDoItem) {
            when (toDoItem.status) {
                Status.IN_PROGRESS -> {
                    binding.tvTitle.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.green
                        )
                    )
                    binding.checkbox.isChecked = false
                }
                Status.COMPLETED -> {
                    binding.tvTitle.setTextColor(Color.GRAY)
                    binding.checkbox.isChecked = true
                }
                Status.PENDING -> {
                    binding.tvTitle.setTextColor(Color.BLACK)
                    binding.checkbox.isChecked = false
                }
            }
            binding.tvTitle.text = toDoItem.title
            binding.checkbox.isChecked = toDoItem.status == Status.COMPLETED
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition in currentList.indices && toPosition in currentList.indices) {
            swipeItems(fromPosition, toPosition)
        }
    }
}