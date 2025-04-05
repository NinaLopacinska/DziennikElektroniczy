package com.projekt.dzienniczek.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import com.projekt.dzienniczek.MainActivity
import com.projekt.dzienniczek.R
import com.projekt.dzienniczek.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val currentUser = (activity as MainActivity).auth.currentUser

        if(currentUser == null) {
            findNavController().navigate(R.id.action_nav_home_to_nav_login)
        }

        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        currentUser?.let {
            binding.userType.text = currentUser.displayName
            binding.userEmail.text = currentUser.email
        }

        binding.logout.setOnClickListener{
            (activity as MainActivity).auth.signOut()
            findNavController().navigate(R.id.action_nav_home_to_nav_login)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}