package com.restaurant.foody20.activity.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.restaurant.foody20.activity.MainActivity
import com.restaurant.foody20.databinding.FragmentLogOutBinding

enum class NameType{
    BASIC
}
@Suppress("NAME_SHADOWING")
class LogOutFragment : Fragment() {

    private lateinit var _binding: FragmentLogOutBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLogOutBinding.inflate(inflater, container, false)
        LogOut()
        val args = arguments
        val index = args?.getString("name")
        UserLoged(index.toString())
        return _binding.root

    }

    fun LogOut() {
        binding.btnLogout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val homeIntent: Intent = Intent( this@LogOutFragment.context, MainActivity::class.java ).apply{}
            startActivity(homeIntent)
        }
    }

    fun UserLoged ( name:String ) {
        binding.textusuario.text = "Espero que regreses pronto $name"
    }

}