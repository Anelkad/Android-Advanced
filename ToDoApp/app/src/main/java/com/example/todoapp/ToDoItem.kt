package com.example.todoapp

data class ToDoItem(
    val id: Int,
    val title: String,
    val status: Status = Status.PENDING
){
    enum class Status {
        COMPLETED, IN_PROGRESS, PENDING;
    }
}
