package com.example.todoapp.domain.repository

import com.example.todoapp.models.ToDoItem
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {
    val toDoList: Flow<List<ToDoItem>>
    suspend fun insert(toDoItem: ToDoItem)

    suspend fun deleteItem(id: Int)

    suspend fun updateItem(toDoItem: ToDoItem)
}