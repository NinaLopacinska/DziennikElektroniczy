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

    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val loginViewModel =
            ViewModelProvider(this).get(LoginViewModel::class.java)

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        loginViewModel.text.observe(viewLifecycleOwner) {
            binding.textLogin.text = it
        }

        binding.buttonLogin.setOnClickListener{
            activity?.let { act ->
                (act as MainActivity).auth.signInWithEmailAndPassword(binding.editTextLogin.text.toString(), binding.editTextPassword.text.toString())
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
        }

        binding.buttonLoginStudent.setOnClickListener{
            binding.editTextLogin.setText("marysiama2003@gmail.com")
            binding.editTextPassword.setText("Haslo123")
        }

        binding.buttonLoginTeacher.setOnClickListener{
            binding.editTextLogin.setText("marysiama2003@gmail.com")
            binding.editTextPassword.setText("Haslo123")
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}