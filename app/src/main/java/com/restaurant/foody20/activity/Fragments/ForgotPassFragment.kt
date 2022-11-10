package com.restaurant.foody20.activity.Fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.restaurant.foody20.activity.MainActivity
import com.restaurant.foody20.activity.NameType
import com.restaurant.foody20.databinding.FragmentForgotPassBinding
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class ForgotPassFragment : Fragment() {

    private var _binding: FragmentForgotPassBinding? = null
    private val binding get() = _binding!!
    val mAuth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    var nameUser = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgotPassBinding.inflate(inflater, container, false)
        resetPass()
        return binding.root

    }

    private fun resetPass() {
        binding.btnreset.setOnClickListener{
            var email = binding.inputemail.text.toString()

            if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(getActivity(), "Ingrese un email valido.",
                    Toast.LENGTH_SHORT).show()
            } else{
                mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener{
                        //Toast.makeText(getActivity(), "Se ha enviado el correo correctamente.",
                            //Toast.LENGTH_SHORT).show()
                        //val homeIntent: Intent = Intent( this@ForgotPassFragment.context, MainActivity::class.java ).apply {}
                       // startActivity(homeIntent)
                        showHome(email)
                    }
                    .addOnFailureListener{
                        Toast.makeText(getActivity(), "Hubo un error enviando el correo.",
                            Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    private fun showHome(email: String){
        db.collection("usuarios")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { result ->
                for (document in result){
                    Log.d("TAG", "${document.id} => ${document.data.get("name")}" )
                    Toast.makeText(getActivity(), "Se ha enviado el correo correctamente.", Toast.LENGTH_SHORT).show()
                    nameUser = document.data.get("name").toString()
                    println("ESTE ES EL NOMBRE QUE TRAE $nameUser")
                    val homeIntent: Intent = Intent( this@ForgotPassFragment.context, MainActivity::class.java ).apply {}

                startActivity(homeIntent)
                }
            }
    }


}