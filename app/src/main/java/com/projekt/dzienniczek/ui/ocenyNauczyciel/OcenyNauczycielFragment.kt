package com.projekt.dzienniczek.ui.ocenyNauczyciel

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.projekt.dzienniczek.MainActivity
import com.projekt.dzienniczek.R
import com.projekt.dzienniczek.databinding.FragmentOcenyNauczycielBinding
import com.projekt.dzienniczek.model.Role
import com.projekt.dzienniczek.model.Subject
import com.projekt.dzienniczek.model.User
import java.util.Calendar
import java.util.Date
import java.util.TimeZone


class OcenyNauczycielFragment : Fragment() {

    private lateinit var binding: FragmentOcenyNauczycielBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val currentUser = (activity as MainActivity).auth.currentUser

        val viewModel =
            ViewModelProvider(this).get(OcenyNauczycielViewModel::class.java)

        binding = FragmentOcenyNauczycielBinding.inflate(inflater, container, false)

        val root: View = binding.root

        viewModel.errorMessageLiveData.observe(viewLifecycleOwner) {
            (activity as MainActivity).auth.signOut()
            findNavController().navigate(R.id.action_nav_home_to_nav_login)
            Toast.makeText(context, it, Toast.LENGTH_SHORT,).show()
        }

        viewModel.messageLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT,).show()
        }

        viewModel.userSubjectAndUserAndSchooldataLiveData.observe(viewLifecycleOwner) { (subject, user, schooldata) ->
            if (subject != null && user != null && schooldata != null) {
                val userSubject = subject.first { it.id_uzyt == currentUser?.uid }

                val adapterSubject =
                    PrzedmiotyAdapter(activity as MainActivity, subject.toMutableList())
                binding.spinnerPrzedmiot.adapter = adapterSubject
                binding.spinnerPrzedmiot.setSelection(adapterSubject.getPosition(userSubject), false)

                val adapterStudent = StudentAdapter(
                    activity as MainActivity,
                    user.filter { it.rola == Role.UCZEN.value }.toMutableList()
                )
                binding.spinnerStudent.adapter = adapterStudent

                val adapterKlasy =
                    KlasyAdapter(activity as MainActivity, schooldata.toMutableList())
                binding.spinnerClass.adapter = adapterKlasy
                binding.spinnerClass.onItemSelectedListener = object : OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        adapterStudent.clear()
                        adapterStudent.addAll(user.filter { it.rola == Role.UCZEN.value })
                    }

                    override fun onItemSelected(
                        parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        adapterStudent.clear()
                        adapterStudent.addAll(user.filter { it.rola == Role.UCZEN.value && it.id_klasa.toString() == adapterKlasy.getItem(position)?.id_klasa })
                    }
                }


                val adapterOcena = OcenaAdapter(
                    activity as MainActivity,
                    listOf("6", "5.5", "5", "4.5", "4", "3.5", "3", "2.5", "2", "1.5", "1").toMutableList()
                )
                binding.gradeValue.adapter = adapterOcena

                var selectedDate: Date? = null

                val cal: Calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"))

                val datePicker = DatePickerDialog(
                    requireContext(),
                    { date, _, _, _ ->
                        val calendar = Calendar.getInstance()
                        calendar.set(date.year, date.month, date.dayOfMonth)
                        selectedDate = calendar.time
                        binding.selectData.text = DateFormat.format("dd/MM/yyy", calendar.time)
                    },
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                )

                binding.buttonSend.setOnClickListener {
                    if (selectedDate != null
                        && (binding.spinnerStudent.selectedItem as User).id != null
                        && (binding.spinnerPrzedmiot.selectedItem as Subject).id != null
                    ) {
                        viewModel.sentGrade(
                            przedmiot = (binding.spinnerPrzedmiot.selectedItem as Subject).id!!,
                            uczen = (binding.spinnerStudent.selectedItem as User).id!!,
                            date = selectedDate!!,
                            ocena = (binding.gradeValue.selectedItem as String).toDouble(),
                            nauczyciel = currentUser!!.uid
                        )
                    } else {
                        Toast.makeText(
                            context,
                            "Pola nie mogą być puste",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }

                binding.selectData.setOnClickListener {
                    datePicker.show()
                }

                binding.progressLoader.visibility = View.GONE
            }
        }

        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.getSubject()
        viewModel.getClass()
        viewModel.getUserData()
        binding.progressLoader.visibility = View.VISIBLE

        return root
    }

}