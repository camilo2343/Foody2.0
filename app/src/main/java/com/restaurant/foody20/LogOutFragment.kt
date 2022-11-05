package com.restaurant.foody20

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.restaurant.foody20.activity.MainActivity
import com.restaurant.foody20.databinding.FragmentLogOutBinding


class LogOutFragment : Fragment() {

    private lateinit var _binding: FragmentLogOutBinding
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLogOutBinding.inflate(inflater, container, false)
        LogOut()
        return _binding.root

    }

    fun LogOut() {
        binding.btnLogout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val homeIntent: Intent = Intent( this@LogOutFragment.context, MainActivity::class.java ).apply{}
            startActivity(homeIntent)
        }
    }


}