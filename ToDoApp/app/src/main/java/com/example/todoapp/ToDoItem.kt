package com.example.todoapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ToDoItem(
    val id: Int = currentId++,
    var title: String,
    var status: Status = Status.PENDING
): Parcelable
{
    companion object {
        var currentId = 1
    }
    enum class Status {
        COMPLETED, IN_PROGRESS, PENDING;
    }
}
