package com.example.todo.network

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.todo.db.AppDbDao
import com.example.todo.model.TodoItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RetroRepository @Inject constructor(
    private val retroServiceInterface: RetroServiceInterface,
    private val appDbDao: AppDbDao
) {

    fun getAllRecords(): LiveData<List<TodoItem>> {
        return appDbDao.getAllRecords()
    }

    fun insertRecord(todoItem: TodoItem) {
        return appDbDao.insertRecord(todoItem)
    }

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun apiCall() {
        val call: Call<List<TodoItem>> = retroServiceInterface.getAllFromAPI()
        call.enqueue(object : Callback<List<TodoItem>> {
            override fun onResponse(
                call: Call<List<TodoItem>>,
                response: Response<List<TodoItem>>
            ) {
                coroutineScope.launch {
                    appDbDao.deleteAllRecords()
                    Log.d("apicall", "get all " + response.body().toString())
                    response.body()?.forEach {
                        insertRecord(it)
                    }
                }
            }

            override fun onFailure(call: Call<List<TodoItem>>, t: Throwable) {
                Log.d("apicall", "get all " + t.message.toString())
            }
        })
    }

    fun addItem(item: TodoItem) {

        coroutineScope.launch {
            val max = appDbDao.getMaxId()
            item.id = (max.toInt() + 1).toString()
            item.userID = "fakeUid"
            appDbDao.insertRecord(item)
        }

        val call: Call<ResponseBody> = retroServiceInterface.postItem(item)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d("apicall", "add item " + response.message())
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("apicall", "add item " + t.message.toString())
            }
        })
    }

    fun updateItem(item: TodoItem) {

        coroutineScope.launch {
            appDbDao.insertRecord(item)
            Log.d("apicall", "update item " + item.id + " " + item.completed)
        }

        val call: Call<ResponseBody> = retroServiceInterface.updateItem(item.id, item)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d("apicall", "update item " + response.message())
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("apicall", "update item " + t.message.toString())
            }
        })
    }

    fun deleteItem(id: String) {

        coroutineScope.launch {
            appDbDao.deleteItemWithId(id)
        }

        val call: Call<Void> = retroServiceInterface.deleteItem(id)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.d("apicall", "delete item " + response.message())
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("apicall", "delete item " + t.message.toString())
            }
        })
    }

    fun searchItem(id: String) :TodoItem {
        return appDbDao.searchItemWithId(id)
    }

}