package com.example.todoapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.todoapp.databinding.FragmentToDoListBinding
import java.util.Collections
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.todoapp.models.Status
import com.example.todoapp.models.ToDoItem
import com.example.todoapp.ui.adapter.ItemMoveCallback
import com.example.todoapp.ui.adapter.ToDoListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ToDoListFragment : Fragment() {
    companion object {
        fun newInstance() = ToDoListFragment()
    }

    private var _binding: FragmentToDoListBinding? = null
    private val binding
        get() = _binding!!


    private val toDoViewModel: ToDoViewModel by viewModels()

    private val adapter: ToDoListAdapter by lazy {
        ToDoListAdapter(
            onToDoItemClicked = ::showEditItemDialog,
            deleteItem = ::deleteItem,
            changeStatus = ::changeStatus,
            changeCheckboxStatus = ::changeCheckboxStatus,
            swipeItems = ::swipeItems
        )
    }


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
        setupObservers()
    }

    private fun setupObservers() {
        toDoViewModel.toDoList.observe(viewLifecycleOwner, Observer { list ->
            list?.let { adapter.submitList(it) }
        })
    }

    private fun bindViews() {
        with(binding) {
            rvList.adapter = adapter

            btnAddToDo.setOnClickListener {
                if (editText.text.isNotEmpty()) {
                    toDoViewModel.insert(
                        ToDoItem(
                            title = editText.text.toString(),
                            status = Status.PENDING
                        )
                    )
                }
                editText.text.clear()
            }

            val itemTouchHelperCallback = ItemMoveCallback(adapter)
            val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
            itemTouchHelper.attachToRecyclerView(binding.rvList)
        }
    }

    private fun deleteItem(id: Int) {
        toDoViewModel.deleteItem(id)
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
        toDoViewModel.updateItem(item)
    }

    private fun changeStatus(id: Int) {
        val position = adapter.currentList.indexOfFirst { it.id == id }
        val status = when (adapter.currentList[position].status) {
            Status.IN_PROGRESS -> Status.COMPLETED
            Status.PENDING -> Status.IN_PROGRESS
            else -> Status.COMPLETED
        }
        val newItem = adapter.currentList[position].copy(status = status)
        toDoViewModel.updateItem(newItem)
    }

    private fun changeCheckboxStatus(id: Int, isChecked: Boolean) {
        val position = adapter.currentList.indexOfFirst { it.id == id }
        val status = if (isChecked) Status.COMPLETED else Status.PENDING
        val newItem = adapter.currentList[position].copy(status = status)
        toDoViewModel.updateItem(newItem)
    }

    private fun swipeItems(fromPosition: Int, toPosition: Int) {
        Collections.swap(adapter.currentList, fromPosition, toPosition)
        adapter.notifyItemMoved(fromPosition, toPosition)
    }
}