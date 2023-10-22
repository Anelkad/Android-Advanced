package com.example.todoapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.todoapp.databinding.FragmentToDoListBinding
import java.util.Collections

class ToDoListFragment : Fragment() {
    companion object {
        fun newInstance() = ToDoListFragment()
    }

    private var _binding: FragmentToDoListBinding? = null
    private val binding
        get() = _binding!!

    private val adapter: ToDoListAdapter by lazy {
        ToDoListAdapter(
            onToDoItemClicked = ::showEditItemDialog,
            deleteItem = ::deleteItem,
            changeStatus = ::changeStatus,
            swipeItems = ::swipeItems
        ).apply {
            submitList(toDoList)
        }
    }
    private var toDoList = mutableListOf(
        ToDoItem(
            title = "to do 1",
            status = ToDoItem.Status.IN_PROGRESS
        ),
        ToDoItem(
            title = "to do 2",
            status = ToDoItem.Status.PENDING
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentToDoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
    }

    private fun bindViews() {
        with(binding) {
            rvList.adapter = adapter

            btnAddToDo.setOnClickListener {
                if (editText.text.isNotEmpty()) {
                    val newList = adapter.currentList.toMutableList()
                    newList.add(
                        ToDoItem(
                            title = editText.text.toString(),
                            status = ToDoItem.Status.PENDING
                        )
                    )
                    adapter.submitList(newList)
                }
                editText.text.clear()
            }

            val itemTouchHelperCallback = ItemMoveCallback(adapter)
            val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
            itemTouchHelper.attachToRecyclerView(binding.rvList)
        }
    }

    private fun deleteItem(id: Int) {
        val newList = adapter.currentList.filter { it.id != id }
        adapter.submitList(newList)
    }

    private fun showEditItemDialog(item: ToDoItem) {
        EditToDoItemFragment.newInstance(item).apply {
            saveItemChanges = {
                updateItem(it)
            }
        }.also {
            it.show(childFragmentManager, it.tag)
        }
    }

    private fun updateItem(item: ToDoItem) {
        val position =  adapter.currentList.indexOfFirst { it.id == item.id }
        val newList = adapter.currentList.toMutableList()
        newList[position] = item
        adapter.submitList(newList)
    }

    private fun changeStatus(id: Int) {
        val position = adapter.currentList.indexOfFirst { it.id == id }
        val status = when (adapter.currentList[position].status) {
            ToDoItem.Status.IN_PROGRESS -> ToDoItem.Status.COMPLETED
            ToDoItem.Status.PENDING -> ToDoItem.Status.IN_PROGRESS
            else -> ToDoItem.Status.COMPLETED
        }
        val newList = adapter.currentList.toMutableList()
        newList[position] = newList[position].copy(status = status)
        adapter.submitList(newList)
    }

    private fun swipeItems(fromPosition: Int, toPosition: Int) {
        Collections.swap(adapter.currentList, fromPosition, toPosition)
        adapter.notifyItemMoved(fromPosition, toPosition)
    }
}