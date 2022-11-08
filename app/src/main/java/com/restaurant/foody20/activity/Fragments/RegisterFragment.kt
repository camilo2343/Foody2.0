package com.restaurant.foody20.activity.Fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.restaurant.foody20.activity.MainActivity
import com.restaurant.foody20.databinding.FragmentRegisterBinding

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

            if (name.isNotEmpty() && lastname.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty()){
                val user = hashMapOf(
                    "name" to name,
                    "lastname" to lastname,
                    "email" to email
                )
                db.collection("usuarios").document()
                    .set(user)
                    .addOnSuccessListener {documentReference ->
                        Log.d("TAG","Se creo correctamente")
                        println("NOMBRE $name APELLIDO $lastname EMAIL $email PASSWORD $pass")
                        FirebaseAuth.getInstance()
                            .createUserWithEmailAndPassword(email.trim(),pass.trim()).addOnCompleteListener{
                                if (it.isSuccessful){
                                    Log.d("TAG", "Se creo correctamente")
                                    sendLogin()
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

            }else{
                showAlertEmpty()
            }

        }



    }

    private fun sendLogin(){
        val loginIntent: Intent = Intent( this@RegisterFragment.context, MainActivity::class.java ).apply {}
        startActivity(loginIntent)
    }

    private fun showAlertEmpty() {
        val constructor = AlertDialog.Builder(this.context)
        constructor.setTitle("Error")
        constructor.setMessage("Algun campo vacio")
        constructor.setPositiveButton("Aceptar", null)
        val dialogo: AlertDialog = constructor.create()
        dialogo.show()
    }



}