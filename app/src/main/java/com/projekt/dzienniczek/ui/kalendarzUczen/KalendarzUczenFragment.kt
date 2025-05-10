package com.projekt.dzienniczek.ui.kalendarzUczen

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.projekt.dzienniczek.MainActivity
import com.projekt.dzienniczek.R
import com.projekt.dzienniczek.databinding.FragmentKalendarzUczenBinding
import com.projekt.dzienniczek.model.AdapterViewType
import com.projekt.dzienniczek.model.ListEvents
import java.util.Calendar
import java.util.TimeZone

class KalendarzUczenFragment : Fragment() {

    private lateinit var binding: FragmentKalendarzUczenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val currentUser = (activity as MainActivity).auth.currentUser

        val viewModel =
            ViewModelProvider(this).get(KalendarzUczenViewModel::class.java)

        binding = FragmentKalendarzUczenBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.errorMessageLiveData.observe(viewLifecycleOwner) {
            (activity as MainActivity).auth.signOut()
            findNavController().navigate(R.id.action_nav_home_to_nav_login)
            Toast.makeText(context, it, Toast.LENGTH_SHORT,).show()
        }

        viewModel.userSubjectAndUserAndEventDataLiveData.observe(viewLifecycleOwner) { (subject, user, event) ->
            if(subject != null && user != null && event != null) {
                val userData = user.first { it.id == currentUser?.uid }

                val userEvent = event.filter {
                    it.id_klasa == userData.id_klasa
                            && it.data!! > Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris")).time
                }. sortedBy { it.data }

                val eventList = emptyList<ListEvents>().toMutableList()


                subject.forEach { s ->
                    val listEventForSubject = userEvent.filter { it.id_przedmiotu == s.id }.map {
                        ListEvents(
                            viewType = AdapterViewType.VIEW_TYPE_ITEM,
                            name = DateFormat.format("dd/MM/yyy", it.data!!).toString(),
                            value = it.opis
                        )
                    }

                    if(listEventForSubject.isNotEmpty()) {
                        eventList.add(
                            ListEvents(
                                viewType = AdapterViewType.VIEW_TYPE_HEADER,
                                name = s.nazwa.orEmpty(),
                                value = null
                            )
                        )

                        eventList.addAll(listEventForSubject)
                    }
                }

                binding.recyclerView.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = WydarzeniaAdapter(eventList)
                }

                binding.emptyList.visibility = if(eventList.isEmpty()) View.VISIBLE else View.GONE

                binding.progressLoader.visibility = View.GONE
            }
        }

        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.getSubject()
        viewModel.getEvent()
        viewModel.getUserData()
        binding.progressLoader.visibility = View.VISIBLE

        return root
    }
}