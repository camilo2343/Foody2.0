package com.restaurant.foody20.activity

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.google.firebase.firestore.FirebaseFirestore
import com.restaurant.foody20.R
import com.restaurant.foody20.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)


        binding.recyclerViewCat.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        }



        fechDataCat()
    }

    private fun fechDataCat() {
        FirebaseFirestore.getInstance().collection("categorys")
            .get()
            .addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.d(TAG, "${document.id} => ${document.data}")
                        val categorias = documents.toObjects(CategoryModel::class.java)

                        binding.recyclerViewCat.adapter = CategoryAdapter(
                            context = this,
                            categories = categorias
                        )
                    }

            }
            .addOnFailureListener{
                showToast("Error ocurrido: ${it.localizedMessage}")
            }
    }

}


