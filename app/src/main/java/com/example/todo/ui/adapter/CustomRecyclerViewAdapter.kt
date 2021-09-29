package com.example.todo.ui.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.model.TodoItem
import kotlinx.android.synthetic.main.recycler_view_row.view.*


class CustomRecyclerViewAdapter : RecyclerView.Adapter<CustomRecyclerViewAdapter.ViewHolder>() {

    private var list: List<TodoItem>? = null
    private lateinit var checkBoxListener: OnCheckBoxListener

    fun setListData(listData: List<TodoItem>?) {
        this.list = listData
    }

    fun setOnCheckBoxListener(listener: OnCheckBoxListener) {
        this.checkBoxListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list?.let {
            holder.checkBox.setOnCheckedChangeListener(null)
            holder.bind(it[position])
            holder.checkBox.setOnCheckedChangeListener { _, b ->
                it[position].completed = b
                checkBoxListener.onCheckBoxChanged(it[position])
            }
        }
    }

    override fun getItemCount(): Int {
        if (list == null) return 0
        return list?.size!!
    }

    fun getItemIdWithPosition(position: Int): String {
        if (list == null) return ""
        return list!![position].id
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.rv_title
        val checkBox = view.rv_check_box

        fun bind(item: TodoItem) {
            title.text = item.title
            checkBox.isChecked = item.completed
            if (item.completed)
                title.paintFlags.apply { this or Paint.STRIKE_THRU_TEXT_FLAG }
            else
                title.paintFlags.apply { this and (Paint.STRIKE_THRU_TEXT_FLAG).inv() }
        }
    }

    interface OnCheckBoxListener {
        fun onCheckBoxChanged(todoItem: TodoItem)
    }
}