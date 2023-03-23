package com.example.todolistapp.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistapp.model.Task
import com.example.todolistapp.databinding.CardItemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class TaskAdapter(var taskList : ArrayList<Task>) : RecyclerView.Adapter<TaskAdapter.taskHolder>() {
    private val ref = FirebaseDatabase.getInstance().getReference("Tasks")
    private val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
    inner class taskHolder(val binding : CardItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): taskHolder {
        val design = CardItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return taskHolder(design)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }


    override fun onBindViewHolder(holder: taskHolder, position: Int) {
        val sortedList = taskList.sortedByDescending { it.priority }
        val task =sortedList[position]
        holder.binding.task = task

        if(task.priority!!){
            holder.binding.ivPriority.visibility = View.VISIBLE
        }else{
            holder.binding.ivPriority.visibility = View.INVISIBLE
        }

        val isVisible = task.visibility
        holder.binding.clExpand.visibility = if(isVisible)  View.VISIBLE  else  View.GONE


        holder.binding.tvTitle.setOnClickListener {
            task.visibility = !task.visibility
            notifyItemChanged(position)
        }

        holder.binding.ibRemove.setOnClickListener { removeTask(task) }


    }

    private fun removeTask(task : Task) {
        ref.child(uid).child(task.id).removeValue()
    }


}