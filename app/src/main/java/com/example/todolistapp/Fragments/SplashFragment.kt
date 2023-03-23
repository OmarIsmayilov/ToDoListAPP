package com.example.todolistapp.Fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.todolistapp.R
import com.example.todolistapp.databinding.FragmentRegisterBinding
import com.example.todolistapp.databinding.FragmentSplashBinding
import com.google.firebase.auth.FirebaseAuth


class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
    private var auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.myLooper()!!).postDelayed({
            if(auth.currentUser!=null){
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToMainFragment())
            }else{
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())

            }
        },3000)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}