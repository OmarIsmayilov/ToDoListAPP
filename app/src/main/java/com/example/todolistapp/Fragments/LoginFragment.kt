package com.example.todolistapp.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.todolistapp.R
import com.example.todolistapp.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private var auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            getData()
        }

        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

    }


    private fun getData() {
        val email = binding.etEmail.text.toString().trim()
        val pass = binding.etPass.text.toString().trim()

        if(TextUtils.isEmpty(email)){
            Toast("Email field cannot be empty.")
        }else if(TextUtils.isEmpty(pass)){
            Toast("Password field cannot be empty")
        }else {
            checkUser(email,pass)
        }



    }

    private fun checkUser(email: String,password: String) {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
            if (it.isSuccessful){
                Toast("Login succesfully")
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainFragment())
            }else{
                Toast(it.exception?.message.toString())
            }
        }
    }


    private fun Toast(text:String){
        android.widget.Toast.makeText(context,text, android.widget.Toast.LENGTH_SHORT).show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}