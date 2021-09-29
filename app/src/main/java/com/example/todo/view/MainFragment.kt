package com.example.todo.view

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.model.TodoItem
import com.example.todo.ui.SwipeToDeleteCallback
import com.example.todo.ui.adapter.CustomRecyclerViewAdapter
import com.example.todo.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_fragment.*

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var customRecyclerViewAdapter: CustomRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        fab_fragment.setOnClickListener {
            addTodoItemDialog()
        }

        initRecyclerView()
        initViewModel()
    }

    private fun initRecyclerView() {
        rv_fragment.apply {
            layoutManager = LinearLayoutManager(requireContext())

            val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
            customRecyclerViewAdapter = CustomRecyclerViewAdapter()
            customRecyclerViewAdapter.setOnCheckBoxListener(object :
                CustomRecyclerViewAdapter.OnCheckBoxListener {
                override fun onCheckBoxChanged(todoItem: TodoItem) {
                    updateItem(todoItem)
                }
            })
            adapter = customRecyclerViewAdapter
        }

        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = rv_fragment.adapter as CustomRecyclerViewAdapter
                deleteItem(adapter.getItemIdWithPosition(viewHolder.adapterPosition))
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(rv_fragment)

        rv_fragment.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 && fab_fragment.isShown)
                    fab_fragment.hide()
                if (dy < 0 && !fab_fragment.isShown)
                    fab_fragment.show()
            }
        })
    }

    private fun initViewModel() {
        viewModel.getAllRepositoryList().observe(viewLifecycleOwner, Observer<List<TodoItem>> {
            customRecyclerViewAdapter.setListData(it)
            customRecyclerViewAdapter.notifyDataSetChanged()
        })
        viewModel.getAllFromApiCall()
    }

    private fun addTodoItemDialog() {
        val editText = EditText(requireContext())
        editText.inputType = InputType.TYPE_CLASS_TEXT

        val alertDialog = AlertDialog.Builder(requireContext())
            .setTitle("Enter Todo:")
            .setView(editText)
            .setNegativeButton(
                "Cancel"
            ) { dialogInterface, _ -> dialogInterface.dismiss() }
            .setPositiveButton(
                "Ok"
            ) { _, _ -> addTodoItem(editText.text.toString()) }
            .create()

        alertDialog.show()
    }

    private fun addTodoItem(text: String) {
        val todoItem = TodoItem()
        todoItem.title = text
        viewModel.addItemWithApi(todoItem)
    }

    private fun deleteItem(id: String) {
        viewModel.deleteItemWithApi(id)
    }

    private fun updateItem(todoItem: TodoItem) {
        viewModel.updateWithApi(todoItem)
    }
}