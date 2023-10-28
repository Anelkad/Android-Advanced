package com.example.todoapp

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ToDoRepositoryImp @Inject constructor(
    private val toDoDao: ToDoDao
) : ToDoRepository {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    override val toDoList: Flow<List<ToDoItem>> = toDoDao.getAll()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    override suspend fun insert(toDoItem: ToDoItem) {
        toDoDao.insertItem(toDoItem)
    }
}