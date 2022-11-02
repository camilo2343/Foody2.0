package com.restaurant.foody20.activity

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.restaurant.foody20.activity.Adaptador.PopulateAdapter
import com.restaurant.foody20.activity.Information.PopulateData
import com.restaurant.foody20.activity.Modelos.CategoryModel
import com.restaurant.foody20.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        dataCategories()
        dataPopulate()
    }

    private fun dataPopulate() {
        val adapter = PopulateAdapter(this, PopulateData.dataPopulate)
        binding?.reciclerPopulares?.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false )
        binding?.reciclerPopulares?.adapter = adapter
    }

    private fun dataCategories() {
        FirebaseFirestore.getInstance().collection("categorys")
            .get()
            .addOnSuccessListener { documents ->
                    for (document in documents) {
                        //Log.d(TAG, "${document.id} => ${document.data}")
                        val categorias = documents.toObjects(CategoryModel::class.java)

                        val adapter  = CategoryAdapter(this, categorias)
                        binding?.recyclerViewCat?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                        binding?.recyclerViewCat?.adapter = adapter
                    }
            }
            .addOnFailureListener{
                showToast("Error ocurrido: ${it.localizedMessage}")
            }
    }

}


