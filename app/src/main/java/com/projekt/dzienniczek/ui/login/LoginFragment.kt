package com.projekt.dzienniczek.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.projekt.dzienniczek.MainActivity
import com.projekt.dzienniczek.R
import com.projekt.dzienniczek.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel =
            ViewModelProvider(this).get(LoginViewModel::class.java)

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.text.observe(viewLifecycleOwner) {
            binding.textLogin.text = it
        }

        binding.buttonLogin.setOnClickListener{
            val login = binding.editTextLogin.text.toString()
            val password =  binding.editTextPassword.text.toString()

            if(login.isNotEmpty() && password.isNotEmpty()) {
                activity?.let { act ->
                    (act as MainActivity).auth.signInWithEmailAndPassword(login, password)
                        .addOnCompleteListener(act) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                findNavController().popBackStack()
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("signInWithEmail:failure", task.exception)
                                Toast.makeText(
                                    context,
                                    "Authentication failed.",
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }
                        }
                }
            } else {
                Toast.makeText(
                    context,
                    "Pola nie mogą być puste",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }

        binding.buttonLoginStudent.setOnClickListener{
            binding.editTextLogin.setText("marysiama2003@gmail.com")
            binding.editTextPassword.setText("Haslo123")
        }

        binding.buttonLoginTeacher.setOnClickListener{
            binding.editTextLogin.setText("anowak@wp.pl")
            binding.editTextPassword.setText("Haslo123")
        }

        return root
    }
}