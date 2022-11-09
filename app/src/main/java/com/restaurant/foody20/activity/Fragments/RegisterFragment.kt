package com.restaurant.foody20.activity.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.restaurant.foody20.activity.MainActivity
import com.restaurant.foody20.databinding.FragmentRegisterBinding
import java.util.regex.Pattern

class RegisterFragment : Fragment() {
    val db = FirebaseFirestore.getInstance()
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        registerUser()
        return binding.root
    }

    private fun registerUser() {
        binding.btnregister.setOnClickListener{
            val name = binding.inputname.text.toString()
            val lastname = binding.inputlastname.text.toString()
            val email = binding.inputemail.text.toString()
            val pass = binding.inputpass.text.toString()

            val passwordRegex = Pattern.compile("^" +
                    "(?=.*[‐@#$%^&+=])" +     // Al menos 1 carácter especial
                    ".{8,}" +                // Al menos 4 caracteres
                    "$")
            if(name.isEmpty()){
                Toast.makeText(getActivity(), "El nombre esta vacio.",
                    Toast.LENGTH_SHORT).show()
            }else if(lastname.isEmpty()) {
                Toast.makeText(getActivity(), "El apellido esta vacio.",
                    Toast.LENGTH_SHORT).show()
            }
            else if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(getActivity(), "Ingrese un email valido.",
                    Toast.LENGTH_SHORT).show()
            } else if (pass.isEmpty() || !passwordRegex.matcher(pass).matches()){
                Toast.makeText(getActivity(), "La contraseña es debil.",
                    Toast.LENGTH_SHORT).show()
            }else {
                CreateUser(name,lastname,email,pass)
            }
        }
    }

    private fun CreateUser(name: String, lastname: String, email: String, pass: String) {
        val user = hashMapOf(
            "name" to name,
            "lastname" to lastname,
            "email" to email
        )
        db.collection("usuarios").document()
            .set(user)
            .addOnSuccessListener {documentReference ->
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(email.trim(),pass.trim()).addOnCompleteListener{
                        if (it.isSuccessful){
                            sendLogin(name)
                        }
                        else {
                            db.collection("usuarios").document(documentReference.toString())
                                .delete()
                                .addOnSuccessListener { Log.d("TAG", "Documento borrado correctamente") }
                                .addOnFailureListener { e -> Log.w("TAG", "Error al borrar el documento", e) }
                        }
                    }
            }
            .addOnFailureListener{
                    e -> Log.w("TAG", "Error $e")
            }
    }

    private fun sendLogin(name: String){
        val loginIntent: Intent = Intent( this@RegisterFragment.context, MainActivity::class.java ).apply {
            putExtra("name",name )
        }
        startActivity(loginIntent)
    }
}