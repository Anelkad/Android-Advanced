package com.example.todoapp

import kotlinx.coroutines.flow.Flow

interface ToDoRepository {
    val toDoList: Flow<List<ToDoItem>>
    suspend fun insert(toDoItem: ToDoItem)
}