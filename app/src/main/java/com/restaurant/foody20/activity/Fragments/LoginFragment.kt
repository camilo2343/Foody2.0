package com.restaurant.foody20.activity.Fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.restaurant.foody20.R
import com.restaurant.foody20.activity.MainActivity
import com.restaurant.foody20.activity.ProviderType
import com.restaurant.foody20.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    val registerFragment = RegisterFragment()
    private lateinit var _binding: FragmentLoginBinding
    private val binding get() = _binding!!

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
        return _binding.root
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
                ///if(esCorreo(email.toString())){ }

                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(email,pass).addOnCompleteListener{
                        if (it.isSuccessful){
                            println("ENTRO LOGIN")
                            showHome(it.result?.user?.email.toString(), ProviderType.BASIC)
                        }else{
                            println("NO ENTRO LOGIN")
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

/*
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
    */


}



