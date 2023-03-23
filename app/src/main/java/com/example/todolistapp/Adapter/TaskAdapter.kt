package com.example.todolistapp.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistapp.Model.Task
import com.example.todolistapp.databinding.CardItemBinding
import java.text.SimpleDateFormat

class TaskAdapter(var taskList : ArrayList<Task>) : RecyclerView.Adapter<TaskAdapter.taskHolder>() {

    inner class taskHolder(val binding : CardItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): taskHolder {
        val design = CardItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return taskHolder(design)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }


    override fun onBindViewHolder(holder: taskHolder, position: Int) {
        val task = taskList[position]
        holder.binding.tvTitle.text = task.title
        holder.binding.tvTime.text = task.time
        if(task.priority!!){
            holder.binding.ivPriority.visibility = View.VISIBLE
        }
    }




}