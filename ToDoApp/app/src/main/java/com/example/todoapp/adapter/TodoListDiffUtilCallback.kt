package com.example.todoapp.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.todoapp.ToDoItem

class ToDoListDiffUtilCallback : DiffUtil.ItemCallback<ToDoItem>() {

    override fun areItemsTheSame(oldItem: ToDoItem, newItem: ToDoItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ToDoItem, newItem: ToDoItem): Boolean {
        return oldItem == newItem
    }
}