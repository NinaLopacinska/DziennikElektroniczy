package com.projekt.dzienniczek.ui.ustawienia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.EmailAuthProvider
import com.projekt.dzienniczek.MainActivity
import com.projekt.dzienniczek.databinding.FragmentUstawieniaBinding
import com.projekt.dzienniczek.utils.AppPreferences

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

        binding.switchPowiadomienia.isChecked = AppPreferences.isNotificationOn

        binding.switchPowiadomienia.setOnCheckedChangeListener { _, isChecked ->
            AppPreferences.isNotificationOn = isChecked
            Toast.makeText(context, "Uruchom aplikację ponownie aby zapisać dane", Toast.LENGTH_SHORT,).show()
        }

        binding.button.setOnClickListener {
            if (binding.newPassword.text.equals(binding.repeatPassword.text)) {
                changePassword(
                    binding.currentPassword.text.toString(),
                    binding.newPassword.text.toString()
                )
            } else {
                Toast.makeText(context, "Password are not the same", Toast.LENGTH_SHORT,).show()
            }
        }

        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        return root
    }

    private fun changePassword(oldPassword: String, newPassword: String) {
        (activity as MainActivity).auth.currentUser?.let { user ->
            val credential = EmailAuthProvider.getCredential(user.email!!, oldPassword)

            user.reauthenticate(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        user.updatePassword(newPassword).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(context, "Password updated", Toast.LENGTH_SHORT,).show()
                            } else {
                                Toast.makeText(context, "Error password not updated", Toast.LENGTH_SHORT,).show()
                            }
                        }
                    } else {
                        Toast.makeText(context, "Error auth failed", Toast.LENGTH_SHORT,).show()
                    }
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}