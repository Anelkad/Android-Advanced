package com.example.todoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.todoapp.databinding.FragmentEditToDoItemBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EditToDoItemFragment(

) : BottomSheetDialogFragment() {

    companion object {
        private const val ARG_TO_DO_ITEM = "toDoItem"

        fun newInstance(toDoItem: ToDoItem) = EditToDoItemFragment().apply {
            arguments = bundleOf(IntentConstants.TO_DO_ITEM to toDoItem)
        }
    }

    private var binding: FragmentEditToDoItemBinding? = null
    private var toDoItem: ToDoItem? = null

    var saveItemChanges: ((ToDoItem) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditToDoItemBinding.inflate(inflater, container, false)
        toDoItem = arguments?.getParcelable(IntentConstants.TO_DO_ITEM)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
    }

    override fun onResume() {
        super.onResume()
        toDoItem = arguments?.getParcelable<ToDoItem>(IntentConstants.TO_DO_ITEM)
        binding?.apply {
            editText.setText(toDoItem?.title)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        saveItemChanges = null
    }

    private fun bindViews() {
        binding?.apply {
            editText.setText(toDoItem?.title)
        }
        binding?.btnSave?.setOnClickListener {
            if (binding?.editText?.text?.toString() != null) {
                toDoItem?.copy(
                    title = binding?.editText?.text.toString()
                )?.let { changedItem ->
                    saveItemChanges?.invoke(changedItem)
                }
            }
            dismiss()
        }
    }

}