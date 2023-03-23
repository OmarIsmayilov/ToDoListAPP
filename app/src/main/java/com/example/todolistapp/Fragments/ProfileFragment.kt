package com.example.todolistapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.todolistapp.R
import com.example.todolistapp.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var auth = FirebaseDatabase.getInstance().getReference("Users")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        binding.btnLogout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(requireContext(),"Logged out",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }

    }

    private fun getData() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()


        auth.child(uid).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val name = snapshot.child("name").value.toString()
                binding.tvName.text = name.capitalize(Locale.ROOT)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,error.message, Toast.LENGTH_SHORT).show()
            }


        })

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}