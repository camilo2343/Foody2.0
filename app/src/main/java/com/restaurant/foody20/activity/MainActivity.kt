package com.restaurant.foody20.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.restaurant.foody20.R
import com.restaurant.foody20.activity.Adaptador.PopulateAdapter
import com.restaurant.foody20.activity.Fragments.LogOutFragment
import com.restaurant.foody20.activity.Fragments.LoginFragment
import com.restaurant.foody20.activity.Fragments.RegisterFragment
import com.restaurant.foody20.activity.Information.PopulateData
import com.restaurant.foody20.activity.Modelos.CategoryModel
import com.restaurant.foody20.databinding.ActivityMainBinding

enum class ProviderType{
BASIC
}

@Suppress("NAME_SHADOWING")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val profileFragment = LoginFragment()
    val logoutFragment = LogOutFragment()
    val registerFragment = RegisterFragment()
    private val fragmentManager: FragmentManager = supportFragmentManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val mainActivity = binding.root
        setContentView(mainActivity)

        dataCategories()
        dataPopulate()
        OnActionBtnProfile()
        OnClickBtnHome()

        val bundle: Bundle? = intent.extras
        val user: String? = bundle?.getString("email")
        val provider: String? = bundle?.getString("provider")

        UserLoged(user ?:"", provider ?: "")
    }

    fun OnClickBtnHome(){
        binding.btnhome.setOnClickListener {
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            val count = supportFragmentManager.backStackEntryCount
            if ( count > 0) {
                fragmentTransaction.remove(profileFragment)
                fragmentManager.popBackStack()
                fragmentTransaction.commit()
            }
        }
    }


    fun  OnActionBtnProfile(){
        binding.btnProfile.setOnClickListener(View.OnClickListener {
            val loggedIn = FirebaseAuth.getInstance().currentUser != null
            if (loggedIn){
                val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.activity_main, logoutFragment)
                fragmentTransaction.addToBackStack("login")
                fragmentTransaction.commit()
            }else{
                val count = supportFragmentManager.backStackEntryCount
                if (count == 0 ) {
                    val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.activity_main, profileFragment)
                    fragmentTransaction.addToBackStack("login")
                    fragmentTransaction.commit()
                } else{
                    val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
                    //showToast("Ya esta creado el fragmento login")
                    fragmentTransaction.remove(profileFragment)
                    fragmentManager.popBackStack()
                    fragmentTransaction.commit()
                }
            }
        })
    }




    private fun dataPopulate() {
        val adapter = PopulateAdapter(this, PopulateData.dataPopulate)
        binding.reciclerPopulares.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false )
        binding.reciclerPopulares.adapter = adapter
    }

    private fun dataCategories() {
        FirebaseFirestore.getInstance().collection("categorys")
            .get()
            .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val categorias = documents.toObjects(CategoryModel::class.java)
                        val adapter  = CategoryAdapter(this, categorias)
                        binding.recyclerViewCat.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                        binding.recyclerViewCat.adapter = adapter
                    }
            }
            .addOnFailureListener{
                showToast("Error ocurrido: ${it.localizedMessage}")
            }
    }
    fun UserLoged ( email:String, provider: String ) {
        binding.textCorreo.text = email

    }
}




