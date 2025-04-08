package com.projekt.dzienniczek.ui.ocenyNauczyciel

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import java.text.DecimalFormat
import java.util.Calendar
import java.util.Date
import java.util.TimeZone


class OcenyNauczycielFragment : Fragment() {

    private var _binding: FragmentOcenyNauczycielBinding? = null

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
            ViewModelProvider(this).get(OcenyNauczycielViewModel::class.java)

        _binding = FragmentOcenyNauczycielBinding.inflate(inflater, container, false)
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

        viewModel.userSubjectAndUserAndSchooldataLiveData.observe(viewLifecycleOwner) { (subject, user, schooldata) ->
            if(subject != null && user != null && schooldata != null) {
                val userSubject = subject.first { it.id_uzyt == currentUser?.uid }

                val adapter = PrzedmiotyAdapter(activity as MainActivity, subject.toTypedArray())
                binding.spinnerPrzedmiot.adapter = adapter
                binding.spinnerPrzedmiot.setSelection(adapter.getPosition(userSubject), false)

                val adapter2 = KlasyAdapter(activity as MainActivity, schooldata.toTypedArray())
                binding.spinnerClass.adapter = adapter2

                val adapter3 = StudentAdapter(activity as MainActivity, user.filter { it.rola == Role.UCZEN.value }.toTypedArray())
                binding.spinnerStudent.adapter = adapter3

                val adapter4 = OcenaAdapter(activity as MainActivity, listOf("6", "5.5", "5","4.5", "4", "3.5", "3", "2.5", "2", "1.5", "1").toTypedArray())
                binding.gradeValue.adapter = adapter4

                var selectedDate: Date? = null

                val cal: Calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"))

                val datePicker = DatePickerDialog(
                    requireContext(),
                    { date, y, m, d ->
                        val calendar = Calendar.getInstance()
                        calendar.set(date.year, date.month, date.dayOfMonth)
                        selectedDate = calendar.time
                        binding.selectData.text = d.toString() + "." + m.toString() + "." + y.toString()
                    },
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                )

                binding.buttonSend.setOnClickListener {
                    if(
                        selectedDate != null
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}