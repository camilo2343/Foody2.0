package com.restaurant.foody20.activity.Fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.restaurant.foody20.R
import com.restaurant.foody20.activity.MainActivity
import com.restaurant.foody20.activity.ProviderType
import com.restaurant.foody20.databinding.FragmentLoginBinding
import java.security.Provider
import java.util.regex.Matcher
import java.util.regex.Pattern

class LoginFragment : Fragment() {

    private lateinit var _binding: FragmentLoginBinding
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //inicioSesion()
        //return inflater.inflate(R.layout.fragment_login, container, false)
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        inicioSesion()
        return _binding.root
    }



    fun inicioSesion(){

        binding.btnlogin?.setOnClickListener(View.OnClickListener {

            var email = binding.inputemail.text.toString()
            var pass = binding.inputpass.text.toString()

            if ( email?.isNotEmpty() == true && pass?.isNotEmpty() == true){
                ///if(esCorreo(email.toString())){ }

                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(email,pass).addOnCompleteListener{
                        if (it.isSuccessful){
                            showHome(it.result?.user?.email.toString() ?: "", ProviderType.BASIC)
                        }else{
                            showAlertFiled()
                        }

                    }
            }else{
                showAlertEmpty()
            }
        })
    }

    private fun showAlertEmpty() {
        val constructor = AlertDialog.Builder(this.context)
        constructor.setTitle("Error")
        constructor.setMessage("El usuario o la contraseña esta vacio")
        constructor.setPositiveButton("Aceptar", null)
        val dialogo: AlertDialog = constructor.create()
        dialogo.show()
    }

    private fun showHome(email: String, provider: ProviderType){
        val homeIntent: Intent = Intent( this@LoginFragment.context, MainActivity::class.java ).apply {
            putExtra("email", email)
            putExtra("provide", provider.name)
        }
        startActivity(homeIntent)


    }

    fun showAlertFiled(){
        val constructor = AlertDialog.Builder(this.context)
        constructor.setTitle("Error")
        constructor.setMessage("Correo o contraseña incorrectos")
        constructor.setPositiveButton("Aceptar", null)
        val dialogo: AlertDialog = constructor.create()
        dialogo.show()
    }


    fun esCorreo(texto:String):Boolean{
        var patroncito: Pattern =Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
        var comparador: Matcher =patroncito.matcher(texto)
        return comparador.find()
    }

    fun isPassword(texto:String): Boolean{
        var minus: Pattern =Pattern.compile("[a-z]")
        var mayus: Pattern =Pattern.compile("[A-Z]")
        var num: Pattern =Pattern.compile(".*\\\\d.*")
        var simbolos: Pattern =Pattern.compile(".*[!@#\$%^&*+=?-].*")
        var espacios: Pattern = Pattern.compile(".*\\\\s.*")
        var control:Boolean = true

        return true

    }

}

