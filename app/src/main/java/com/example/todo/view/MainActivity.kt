package com.example.todo.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.todo.R
import com.example.todo.model.TodoItem
import com.example.todo.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_activity.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
//                Snackbar.make(container, "search", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
                searchDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun searchDialog() {
        val editText = EditText(this)
        editText.inputType = InputType.TYPE_CLASS_NUMBER

        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Search with id:")
            .setView(editText)
            .setNegativeButton(
                "Cancel"
            ) { dialogInterface, _ -> dialogInterface.dismiss() }
            .setPositiveButton(
                "Ok"
            ) { _, _ -> searchWithId(editText.text.toString()) }
            .create()

        alertDialog.show()
    }

    private fun searchWithId(id: String) {
        val todoItem: TodoItem = viewModel.searchItem(id)

        if (todoItem == null) {
            Snackbar.make(container, "ID($id) not found", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            return
        }

        Log.d("main activity", todoItem.toString())
        val intent = Intent(this, MainActivity2::class.java)
        intent.putExtra("ITEM_TAG", todoItem)
        startActivity(intent)
    }
}