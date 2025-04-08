package com.projekt.dzienniczek.ui.wiadomosci

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
import com.projekt.dzienniczek.databinding.FragmentWiadomosciListaBinding
import com.projekt.dzienniczek.model.AdapterViewType
import com.projekt.dzienniczek.model.ListaKontaktow
import com.projekt.dzienniczek.model.Role
import com.projekt.dzienniczek.model.User

class WiadomosciListaFragment : Fragment() {

    private var _binding: FragmentWiadomosciListaBinding? = null

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
            ViewModelProvider(this).get(WiadomosciListaViewModel::class.java)

        _binding = FragmentWiadomosciListaBinding.inflate(inflater, container, false)
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

        viewModel.userDataAndSchoolClassLiveData.observe(viewLifecycleOwner) { (userList, schoolClass) ->
            if(userList != null && schoolClass != null) {
                val userData = userList.filter { it.email != currentUser?.email }

                val allUsersEmail: List<String?> = userData.map { it.email }
                val students: List<User> = userData.filter { it.rola == Role.UCZEN.value }
                val studentsEmail: List<String?> = students.map { it.email }
                val teachers: List<User> = userData.filter { it.rola == Role.NAUCZYCIEL.value }
                val teachersEmail: List<String?> = teachers.map { it.email }

                val contactList = emptyList<ListaKontaktow>().toMutableList()
                contactList.add(
                    ListaKontaktow(
                        name = "Wszyscy",
                        viewType = AdapterViewType.VIEW_TYPE_HEADER,
                        emailList = allUsersEmail
                    )
                )
                contactList.add(
                    ListaKontaktow(
                        name = "Nauczyciele",
                        viewType = AdapterViewType.VIEW_TYPE_HEADER,
                        emailList = teachersEmail
                    )
                )
                contactList.addAll(
                    teachers.map {
                        ListaKontaktow(
                            name = it.imie + " " + it.nazwisko,
                            viewType = AdapterViewType.VIEW_TYPE_ITEM,
                            emailList = listOf(it.email)
                        )
                    }
                )
                contactList.add(
                    ListaKontaktow(
                        name = "Uczniowie",
                        viewType = AdapterViewType.VIEW_TYPE_HEADER,
                        emailList = studentsEmail
                    )
                )
                contactList.addAll(
                    students.map {
                        ListaKontaktow(
                            name = it.imie + " " + it.nazwisko,
                            viewType = AdapterViewType.VIEW_TYPE_ITEM,
                            emailList = listOf(it.email)
                        )
                    }
                )
                contactList.add(
                    ListaKontaktow(
                        name = "Klasy",
                        viewType = AdapterViewType.VIEW_TYPE_HEADER,
                        emailList = studentsEmail
                    )
                )
                schoolClass.forEach { sclass ->
                    contactList.add(
                        ListaKontaktow(
                            name = sclass.nazwa.orEmpty(),
                            viewType = AdapterViewType.VIEW_TYPE_ITEM,
                            emailList = userData.filter { it.id_klasa == sclass.id_klasa }.map { it.email }
                        )
                    )
                }

                Log.d("doc", "onUserDAtaGet " + contactList.toString())

                binding.recyclerView.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = WiadomosciAdapter(contactList) { item ->
                        val bundle = Bundle()
                        bundle.putString("kontakt", Gson().toJson(item))
                        findNavController().navigate(R.id.action_nav_wiadomosci_to_wiadomosci_item, bundle)
                    }
                }

                binding.progressLoader.visibility = View.GONE
            }
        }

        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.getClass()
        viewModel.getUserData()
        binding.progressLoader.visibility = View.VISIBLE

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}