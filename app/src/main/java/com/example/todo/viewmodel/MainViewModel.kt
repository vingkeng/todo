package com.example.todo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.todo.model.TodoItem
import com.example.todo.network.RetroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: RetroRepository) : ViewModel() {

    fun getAllRepositoryList(): LiveData<List<TodoItem>> {
        return repository.getAllRecords()
    }

    fun getAllFromApiCall() {
        repository.apiCall()
    }

    fun addItemWithApi(todoItem: TodoItem) {
        repository.addItem(todoItem)
    }

    fun searchItem(id: String): TodoItem {
        return repository.searchItem(id)
    }

    fun updateWithApi(todoItem: TodoItem) {
        repository.updateItem(todoItem)
    }

    fun deleteItemWithApi(id: String) {
        repository.deleteItem(id)
    }
}