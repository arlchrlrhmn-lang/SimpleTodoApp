package com.example.simpletodoapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.simpletodoapp.databinding.ItemTaskBinding

class TaskAdapter(private val taskList: MutableList<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

        val task = taskList[position]
        holder.binding.cbTask.isChecked = task.isDone
        holder.binding.tvTaskTitle.text = task.title

        holder.binding.cbTask.setOnCheckedChangeListener { _, isChecked ->
            task.isDone = isChecked

            if (task.isDone) {
                holder.binding.tvTaskTitle.paint.isStrikeThruText = true
            } else {
                holder.binding.tvTaskTitle.paint.isStrikeThruText = false
            }
        }


            }
        }

