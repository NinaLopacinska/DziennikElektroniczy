package com.projekt.dzienniczek.ui.ocenyUczen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.projekt.dzienniczek.MainActivity
import com.projekt.dzienniczek.R
import com.projekt.dzienniczek.databinding.FragmentOcenyUczenBinding
import com.projekt.dzienniczek.model.AdapterViewType
import com.projekt.dzienniczek.model.ListaKontaktow
import com.projekt.dzienniczek.model.ListaOcen
import com.projekt.dzienniczek.model.Role
import com.projekt.dzienniczek.model.User
import com.projekt.dzienniczek.ui.wiadomosci.WiadomosciAdapter
import java.text.SimpleDateFormat

class OcenyUczenFragment : Fragment() {

    private var _binding: FragmentOcenyUczenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val currentUser = (activity as MainActivity).auth.currentUser

        val viewModel =
            ViewModelProvider(this).get(OcenyUczenViewModel::class.java)

        _binding = FragmentOcenyUczenBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            (activity as MainActivity).auth.signOut()
            findNavController().navigate(R.id.action_nav_home_to_nav_login)
            Toast.makeText(
                context,
                it,
                Toast.LENGTH_SHORT,
            ).show()
        }

        val format = SimpleDateFormat("dd/MM/yyy")

        viewModel.userSubjectAndGradeLiveData.observe(viewLifecycleOwner) { (subject, grade) ->
            if(subject != null && grade != null) {
                val userGrade = grade.filter { it.id_uzyt == currentUser?.uid }

                val contactList = emptyList<ListaOcen>().toMutableList()

                subject.forEach { s ->
                    val oceny = userGrade.filter { it.id_przedmiotu == s.id }.map {
                        ListaOcen(
                            name = format.format(it.data!!),
                            viewType = AdapterViewType.VIEW_TYPE_HEADER,
                            grade = it.ocena
                        )
                    }

                    var suma = 0.0

                    oceny.forEach { suma += it.grade }

                    contactList.add(
                        ListaOcen(
                            name = s.nazwa.orEmpty(),
                            viewType = AdapterViewType.VIEW_TYPE_HEADER,
                            grade = (suma/oceny.size)
                        )
                    )
                    contactList.addAll(oceny)
                }

                binding.recyclerView.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = OcenyAdapter(contactList)
                }

                binding.progressLoader.visibility = View.GONE
            }
        }

        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.getSubject()
        viewModel.getGrade()
        binding.progressLoader.visibility = View.VISIBLE

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}