package com.projekt.dzienniczek.ui.ustawienia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.projekt.dzienniczek.databinding.FragmentOcenyBinding
import com.projekt.dzienniczek.databinding.FragmentUstawieniaBinding

class UstawieniaFragment : Fragment() {

    private var _binding: FragmentUstawieniaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel =
            ViewModelProvider(this).get(UstawieniaViewModel::class.java)

        _binding = FragmentUstawieniaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.text.observe(viewLifecycleOwner) {
            binding.textRegistration.text = it
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}