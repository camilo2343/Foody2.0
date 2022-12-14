package com.restaurant.foody20.activity.Fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.restaurant.foody20.R
import com.restaurant.foody20.activity.MainActivity
import com.restaurant.foody20.activity.NameType
import com.restaurant.foody20.databinding.FragmentLoginBinding
import java.util.regex.Pattern

class LoginFragment : Fragment() {

    val registerFragment = RegisterFragment()
    val olvidastePass = ForgotPassFragment()
    private lateinit var _binding: FragmentLoginBinding
    private val binding get() = _binding
    val db = FirebaseFirestore.getInstance()
    var nameUser = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        inicioSesion()
        irRegistro()
        irRecuperarPass()
        return _binding.root
    }

    private fun irRecuperarPass() {
        binding.btnForgotPass.setOnClickListener{
            val fragmentTransaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.activity_main, olvidastePass )
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }

    private fun irRegistro() {
        binding.btnregistro.setOnClickListener{
            val fragmentTransaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.activity_main, registerFragment )
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }

    fun inicioSesion(){

        binding.btnlogin.setOnClickListener(View.OnClickListener {

            var email = binding.inputemail.text.toString()
            var pass = binding.inputpass.text.toString()
            println("correo  $email pass  $pass")
            if ( email.isNotEmpty()  && pass.isNotEmpty()){
                val passwordRegex = Pattern.compile("^" +
                        "(?=.*[???@#$%^&+=])" +     // Al menos 1 car??cter especial
                        ".{8,}" +                // Al menos 4 caracteres
                        "$")
                if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(getActivity(), "Ingrese un email valido.",
                        Toast.LENGTH_SHORT).show()
                } else {
                    LoginUser(email,pass)
                }
            }else{
                showAlertEmpty()
            }
        })
    }

    private fun LoginUser(email: String, pass: String) {
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(email,pass).addOnCompleteListener{
                if (it.isSuccessful){
                    println("ENTRO LOGIN")
                    showHome(it.result?.user?.email.toString(), NameType.BASIC)
                }else{
                    println("NO ENTRO LOGIN")
                    showAlertFiled()
                }
            }
    }

    private fun showAlertEmpty() {
        val constructor = AlertDialog.Builder(this.context)
        constructor.setTitle("Error")
        constructor.setMessage("El usuario o la contrase??a esta vacio")
        constructor.setPositiveButton("Aceptar", null)
        val dialogo: AlertDialog = constructor.create()
        dialogo.show()
    }

    private fun showHome(email: String, provider: NameType){
        db.collection("usuarios")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { result ->
                for (document in result){
                    Log.d("TAG", "${document.id} => ${document.data.get("name")}" )
                    nameUser = document.data.get("name").toString()
                    println("ESTE ES EL NOMBRE QUE TRAE $nameUser")
                    val homeIntent: Intent = Intent( this@LoginFragment.context, MainActivity::class.java ).apply {
                        putExtra("name", nameUser)
                    }
                    startActivity(homeIntent)
                }
        }
            .addOnFailureListener{ Exception ->
                Log.d("TAG"," error al obtener los datos $Exception")
            }
    }

    fun showAlertFiled(){
        val constructor = AlertDialog.Builder(this.context)
        constructor.setTitle("Error")
        constructor.setMessage("Correo o contrase??a incorrectos")
        constructor.setPositiveButton("Aceptar", null)
        val dialogo: AlertDialog = constructor.create()
        dialogo.show()
    }
}



