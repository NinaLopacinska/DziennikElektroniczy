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

    private lateinit var binding: FragmentUstawieniaBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUstawieniaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Stan przełącznika powiadomień
        binding.switchPowiadomienia.isChecked = AppPreferences.isNotificationOn

        binding.switchPowiadomienia.setOnCheckedChangeListener { _, isChecked ->
            AppPreferences.isNotificationOn = isChecked
            Toast.makeText(context, "Uruchom aplikację ponownie, aby zapisać dane", Toast.LENGTH_SHORT).show()
        }

        // Obsługa zmiany hasła
        binding.button.setOnClickListener {
            val currentPassword = binding.currentPassword.text.toString()
            val newPassword = binding.newPassword.text.toString()
            val repeatPassword = binding.repeatPassword.text.toString()

            if (newPassword == repeatPassword) {
                if (newPassword.length >= 6) {
                    changePassword(currentPassword, newPassword)
                } else {
                    Toast.makeText(context, "Hasło musi mieć co najmniej 6 znaków", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Hasła nie są takie same", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        return root
    }

    private fun changePassword(oldPassword: String, newPassword: String) {
        val user = (activity as MainActivity).auth.currentUser

        user?.email?.let { email ->
            val credential = EmailAuthProvider.getCredential(email, oldPassword)

            user.reauthenticate(credential).addOnCompleteListener { reauthTask ->
                if (reauthTask.isSuccessful) {
                    user.updatePassword(newPassword).addOnCompleteListener { updateTask ->
                        if (updateTask.isSuccessful) {
                            Toast.makeText(context, "Hasło zostało zmienione", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Błąd: nie udało się zmienić hasła", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(context, "Błąd: niepoprawne obecne hasło", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
