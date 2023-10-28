package com.example.todoapp.di

import com.example.todoapp.ToDoRepository
import com.example.todoapp.ToDoRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelModule {
    @Binds
    @ViewModelScoped
    abstract fun bindsRepository(imp: ToDoRepositoryImp): ToDoRepository
}