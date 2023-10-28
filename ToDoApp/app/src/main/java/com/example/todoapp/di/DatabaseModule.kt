package com.example.todoapp.di

import android.app.Application
import androidx.room.Room
import com.example.todoapp.ToDoDao
import com.example.todoapp.ToDoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    internal fun provideToDoDatabase(application: Application): ToDoDatabase {
        return Room.databaseBuilder(
            application,
            ToDoDatabase::class.java,
            "to_do_table"
        )
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    internal fun provideToDoDao(toDoDatabase: ToDoDatabase): ToDoDao {
        return toDoDatabase.toDoDao()
    }
}