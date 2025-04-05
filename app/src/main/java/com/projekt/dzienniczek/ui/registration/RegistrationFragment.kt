package com.projekt.dzienniczek.ui.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.projekt.dzienniczek.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val registrationViewModel =
            ViewModelProvider(this).get(RegistrationViewModel::class.java)

        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        registrationViewModel.text.observe(viewLifecycleOwner) {
            binding.textRegistration.text = it
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}