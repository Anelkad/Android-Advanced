package com.example.todoapp

data class ToDoItem(
    val id: Int = currentId++,
    val title: String,
    var status: Status = Status.PENDING
){
    companion object {
        var currentId = 1
    }
    enum class Status {
        COMPLETED, IN_PROGRESS, PENDING;
    }
}
