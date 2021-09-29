package com.example.todo.network

import com.example.todo.model.TodoItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface RetroServiceInterface {

    @GET("todo/")
    fun getAllFromAPI(): Call<List<TodoItem>>

    @POST("todo/")
    fun postItem(@Body todoItem: TodoItem): Call<ResponseBody>

    @PUT("todo/{id}")
    fun updateItem(@Path("id") id: String, @Body todoItem: TodoItem): Call<ResponseBody>

    @DELETE("todo/{id}")
    fun deleteItem(@Path("id") id: String): Call<Void>
}