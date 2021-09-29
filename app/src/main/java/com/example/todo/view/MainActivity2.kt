package com.example.todo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todo.R
import com.example.todo.model.TodoItem
import kotlinx.android.synthetic.main.recycler_view_row.*

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val people: TodoItem? = intent.getParcelableExtra("ITEM_TAG")

        people?.let {
            rv_title.text = it.title
            rv_check_box.isChecked = it.completed
        }
    }
}