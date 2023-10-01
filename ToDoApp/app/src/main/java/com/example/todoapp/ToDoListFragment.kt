package com.example.todoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.todoapp.databinding.FragmentToDoListBinding
import java.util.Collections

class ToDoListFragment : Fragment() {
    companion object {
        fun newInstance() = ToDoListFragment()
    }

    var _binding: FragmentToDoListBinding? = null
    private val binding
        get() = _binding!!

    private val editItemDialogFragment = EditToDoItemFragment(saveItemChanges = ::updateItem)

    private val adapter: ToDoListAdapter by lazy {
        ToDoListAdapter(
            onToDoItemClicked = ::showEditItemDialog,
            deleteItem = ::deleteItem,
            editItemDialogFragment = editItemDialogFragment,
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
        binding.rvList.adapter = adapter

        binding.btnAddToDo.setOnClickListener {
            if (binding.editText.text.isNotEmpty()) {
                toDoList.add(
                    ToDoItem(
                        title = binding.editText.text.toString(),
                        status = ToDoItem.Status.PENDING
                    )
                )
                adapter.notifyItemInserted(toDoList.lastIndex)
            }
            binding.editText.text.clear()
        }

        val itemTouchHelperCallback = ItemMoveCallback(adapter)
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvList)

    }

    private fun deleteItem(position: Int){
        toDoList.removeAt(position)
        adapter.notifyItemRemoved(position)
    }

    private fun showEditItemDialog(item: ToDoItem, dialog: EditToDoItemFragment) {
        val bundle = Bundle()
        bundle.putParcelable(IntentConstants.TO_DO_ITEM, item)
        dialog.arguments = bundle
        dialog.show(childFragmentManager, dialog.tag)
    }

    private fun updateItem(item: ToDoItem) {
        val position = toDoList.indexOfFirst { it.id == item.id }
        toDoList[position] = item
        adapter.notifyItemChanged(position)
    }

    private fun swipeItems(fromPosition: Int, toPosition: Int){
        Collections.swap(toDoList, fromPosition, toPosition)
        adapter.notifyItemMoved(fromPosition, toPosition)
    }
}