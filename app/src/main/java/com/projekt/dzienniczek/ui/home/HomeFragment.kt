package com.projekt.dzienniczek.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.projekt.dzienniczek.MainActivity
import com.projekt.dzienniczek.R
import com.projekt.dzienniczek.databinding.FragmentHomeBinding
import com.projekt.dzienniczek.model.Role

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

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

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            (activity as MainActivity).auth.signOut()
            findNavController().navigate(R.id.action_nav_home_to_nav_login)
            Toast.makeText(
                context,
                it,
                Toast.LENGTH_SHORT,
            ).show()
        }

        var rola: Role = Role.UCZEN

        viewModel.userData.observe(viewLifecycleOwner) {
            it.rola?.let { roleId ->
                Role.findRole(roleId)?.let { roleValue ->
                    rola = roleValue
                }
            }

            binding.userType.text = rola.name
            binding.userEmail.text = it.email
            binding.progressLoader.visibility = View.GONE
        }

        binding.oceny.setOnClickListener{
            if(rola == Role.UCZEN) {
                findNavController().navigate(R.id.action_nav_home_to_nav_oceny_uczen)
            } else {
                findNavController().navigate(R.id.action_nav_home_to_nav_oceny_nauczyciel)
            }
        }

        binding.kalendarz.setOnClickListener{
            if(rola == Role.UCZEN) {
                findNavController().navigate(R.id.action_nav_home_to_nav_kalendarz_uczen)
            } else {
                findNavController().navigate(R.id.action_nav_home_to_nav_kalendarz_nauczyciel)
            }
        }

        binding.wiadomosci.setOnClickListener{
            findNavController().navigate(R.id.action_nav_home_to_nav_wiadomosci)
        }

        binding.ustawienia.setOnClickListener{
            findNavController().navigate(R.id.action_nav_home_to_nav_ustawienia)
        }

        binding.logout.setOnClickListener{
            (activity as MainActivity).auth.signOut()
            findNavController().navigate(R.id.action_nav_home_to_nav_login)
        }

        currentUser?.uid?.let {
            viewModel.getUserData(it)
            binding.progressLoader.visibility = View.VISIBLE
        }

        return binding.root
    }
}