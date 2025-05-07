package com.projekt.dzienniczek.ui.kontakty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.projekt.dzienniczek.databinding.FragmentKontaktyBinding

class KontaktyFragment : Fragment() {

    private lateinit var binding: FragmentKontaktyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel =
            ViewModelProvider(this).get(KontaktyViewModel::class.java)

        binding = FragmentKontaktyBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }
}