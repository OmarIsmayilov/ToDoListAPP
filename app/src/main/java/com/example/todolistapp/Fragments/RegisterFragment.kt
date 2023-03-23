package com.example.todolistapp.Fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.todolistapp.model.User
import com.example.todolistapp.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private var auth = FirebaseAuth.getInstance()
    private lateinit var user : User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvSignIn.setOnClickListener { findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()) }

        binding.btnRegister.setOnClickListener { getData() }


    }

    private fun getData() {

        val email = binding.etEmail.text.toString().trim()
        val pass = binding.etPass.text.toString().trim()
        val name = binding.etName.text.toString().trim()
        val coPass = binding.etCoPass.text.toString().trim()


        if(TextUtils.isEmpty(name)){
            Snackbar("Name field cannot be empty")
        }else if(TextUtils.isEmpty(email)) {
            Snackbar("Email field cannot be empty")
        }else if(TextUtils.isEmpty(pass)) {
            Snackbar("Password field cannot be empty")
        }else if(TextUtils.isEmpty(coPass)) {
            Snackbar("Confirm password field cannot be empty")
        }else if(coPass!=pass) {
            Snackbar("Passwords do not match")
        }else if(!binding.cbTerms.isChecked){
            Snackbar("Please accept the terms and conditions")
        }
        else{
            user = User(name,email,pass)
            registerUser(email,pass)
        }




    }

    private fun registerUser(email : String,password:String) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            if(it.isSuccessful){
                Snackbar("Registered Succesfully")
                saveData()
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
            }else{
                Snackbar(it.exception?.message.toString())
            }
        }
    }

    private fun saveData() {
        val ref = FirebaseDatabase.getInstance().reference
        ref.child("Users").child(auth.currentUser?.uid.toString()).setValue(user)
    }

    private fun Snackbar(text:String){
        com.google.android.material.snackbar.Snackbar.make(requireView(),text,
            com.google.android.material.snackbar.Snackbar.LENGTH_SHORT)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}