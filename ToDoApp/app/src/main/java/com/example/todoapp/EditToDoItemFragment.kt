package com.example.todoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todoapp.databinding.FragmentEditToDoItemBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EditToDoItemFragment(
    private val saveItemChanges: (ToDoItem) -> Unit
) : BottomSheetDialogFragment() {

    private var binding: FragmentEditToDoItemBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditToDoItemBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
    }

    override fun onResume() {
        super.onResume()
        val item = arguments?.getParcelable<ToDoItem>("TO_DO_ITEM")
        binding?.apply {
            editText.setText(item?.title)
        }
    }

    private fun bindViews() {
        val item = arguments?.getParcelable<ToDoItem>("TO_DO_ITEM")
        binding?.apply {
            editText.setText(item?.title)
        }
        binding?.btnSave?.setOnClickListener {
            if (binding?.editText?.text?.toString() != null) {
                item?.copy(
                    title = binding?.editText?.text.toString()
                )?.let { changedItem ->
                    saveItemChanges(changedItem)
                }
            }
            dismiss()
        }
    }

}