package com.projekt.dzienniczek.ui.wiadomosci

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.projekt.dzienniczek.MainActivity
import com.projekt.dzienniczek.databinding.FragmentWiadomosciBinding
import com.projekt.dzienniczek.model.ListaKontaktow

class WiadomosciFragment : Fragment() {

    private var _binding: FragmentWiadomosciBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWiadomosciBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val kontakt: ListaKontaktow = Gson().fromJson(arguments?.getString("kontakt"), ListaKontaktow::class.java)

        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.buttonSend.setOnClickListener {
            sentEmail(
                kontakt.emailList.toTypedArray(),
                tytul = binding.tytulValue.text.toString(),
                treść = binding.contentsValue.text.toString()
            )
        }

        return root
    }


    private fun sentEmail(
        emailList: Array<String?>,
        tytul: String,
        treść: String
    ) {
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // tylko aplikacje do e-maili
            putExtra(Intent.EXTRA_EMAIL, emailList)
            putExtra(Intent.EXTRA_SUBJECT, tytul)
            putExtra(Intent.EXTRA_TEXT, treść)
        }

        if (context?.packageManager?.let { emailIntent.resolveActivity(it) } != null) {
            startActivity(emailIntent)
        } else {
            Toast.makeText(context, "Brak aplikacji do obsługi e-maila", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}