package com.example.simpletodoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simpletodoapp.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class MainActivity : AppCompatActivity() {

    private lateinit var tasklist: MutableList<Task>
    private lateinit var adapter: TaskAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadTask()
        adapter = TaskAdapter(tasklist)
        binding.rvTask.adapter = adapter
        binding.rvTask.setHasFixedSize(true)

        binding.rvTask.layoutManager = LinearLayoutManager(this)


        //Swipe delete di luar tombol
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val position = viewHolder.bindingAdapterPosition
                tasklist.removeAt(position)
                adapter.notifyItemRemoved(position)
                saveTask()
            }
        })

        itemTouchHelper.attachToRecyclerView(binding.rvTask)

        //Tombol add
        binding.btnAdd.setOnClickListener {

            val tasktitle = binding.etTask.text.toString()

            if (tasktitle.isNotEmpty()) {
                val task = Task(tasktitle)
                tasklist.add(task)
                adapter.notifyItemInserted(tasklist.size - 1)
                saveTask()
                binding.etTask.text?.clear()
            }
        }
    }

    private fun saveTask() {

        val sharedPreferences = getSharedPreferences("todo_pref", MODE_PRIVATE)

        val editor = sharedPreferences.edit()

        val gson = Gson()

        val json = gson.toJson(tasklist)

        editor.putString("task", json)

        editor.apply()
    }

    private fun loadTask() {

        val sharedPreferences = getSharedPreferences("todo_pref", MODE_PRIVATE)

        val gson = Gson()

        val json = sharedPreferences.getString("task", null)

        val type = object : TypeToken<MutableList<Task>>() {}.type

        if (json != null) {
            tasklist = gson.fromJson(json, type)
        } else {
            tasklist = mutableListOf()
        }
    }
}