package com.projekt.dzienniczek.ui.kalendarzUczen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.projekt.dzienniczek.databinding.FragmentKalendarzUczenBinding

class KalendarzUczenFragment : Fragment() {

    private lateinit var binding: FragmentKalendarzUczenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel =
            ViewModelProvider(this).get(KalendarzUczenViewModel::class.java)

        binding = FragmentKalendarzUczenBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.text.observe(viewLifecycleOwner) {
            binding.textRegistration.text = it
        }

        return root
    }
}