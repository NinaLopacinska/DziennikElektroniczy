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

        val viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var userIsStudent = true

        currentUser?.let {
            binding.userType.text = currentUser.displayName
            binding.userEmail.text = currentUser.email
            userIsStudent = currentUser.email == "anowak@wp.pl"
        }

        if(userIsStudent) {
            binding.frekwencja.visibility = View.VISIBLE
            binding.border14.visibility = View.VISIBLE
            binding.listaObecnosci.visibility = View.GONE
            binding.border7.visibility = View.GONE
        } else {
            binding.frekwencja.visibility = View.GONE
            binding.border14.visibility = View.GONE
            binding.listaObecnosci.visibility = View.VISIBLE
            binding.border7.visibility = View.VISIBLE
        }

        binding.oceny.setOnClickListener{
            findNavController().navigate(R.id.action_nav_home_to_nav_login)
        }

        binding.planZajec.setOnClickListener{
            findNavController().navigate(R.id.action_nav_home_to_nav_login)
        }

        binding.kalendarz.setOnClickListener{
            findNavController().navigate(R.id.action_nav_home_to_nav_login)
        }

        binding.wiadomosci.setOnClickListener{
            findNavController().navigate(R.id.action_nav_home_to_nav_login)
        }

        binding.frekwencja.setOnClickListener{
            findNavController().navigate(R.id.action_nav_home_to_nav_login)
        }

        binding.kontakty.setOnClickListener{
            findNavController().navigate(R.id.action_nav_home_to_nav_login)
        }

        binding.ustawienia.setOnClickListener{
            findNavController().navigate(R.id.action_nav_home_to_nav_login)
        }

        binding.listaObecnosci.setOnClickListener{
            findNavController().navigate(R.id.action_nav_home_to_nav_login)
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