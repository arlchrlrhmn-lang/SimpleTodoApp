package com.example.simpletodoapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simpletodoapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var tasklist: MutableList<Task>
    private lateinit var adapter: TaskAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                binding.etTask.text?.clear()
            }
        }
    }
}