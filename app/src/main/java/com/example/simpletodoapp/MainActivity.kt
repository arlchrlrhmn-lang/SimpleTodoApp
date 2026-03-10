package com.example.simpletodoapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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


    }
}