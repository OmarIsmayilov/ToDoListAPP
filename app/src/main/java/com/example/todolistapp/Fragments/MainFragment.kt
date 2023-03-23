package com.example.todolistapp.Fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.todolistapp.Adapter.TaskAdapter
import com.example.todolistapp.Model.Task
import com.example.todolistapp.R
import com.example.todolistapp.databinding.FragmentMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.collections.ArrayList

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var taskList : ArrayList<Task>
    private var auth = FirebaseAuth.getInstance()
    private lateinit var  dbRef : DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.statusBarColor = Color.parseColor("#0165ff")

        taskList = arrayListOf<Task>()
        getDataFromDb()


        binding.btnAdd.setOnClickListener { showBottomSheet() }
        binding.btnProfile.setOnClickListener{
            auth.signOut()
            findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
        }

    }



    private fun getDataFromDb() {
        dbRef = FirebaseDatabase.getInstance().getReference("Tasks")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                taskList.clear()

                if (snapshot.exists()){

                    for(taskSnapshot in snapshot.children){
                        val task = taskSnapshot.getValue(Task::class.java)
                        taskList.add(task!!)


                    }

                    Log.e("Netice",taskList.toString())
                    binding.rvList.adapter = TaskAdapter(taskList)


                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast(error.message)
            }
        })

    }

    private fun showBottomSheet() {
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToBottomSheetFragment())
    }


    private fun Toast(text:String){
        android.widget.Toast.makeText(context,text, android.widget.Toast.LENGTH_SHORT).show()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}