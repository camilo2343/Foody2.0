package com.restaurant.foody20.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.restaurant.foody20.activity.Adaptador.CatalogAdapter
import com.restaurant.foody20.activity.Modelos.CatalogModel
import com.restaurant.foody20.databinding.ActivityCatalogBinding
import com.restaurant.foody20.databinding.ActivityMainBinding

class CatalogActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCatalogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatalogBinding.inflate(layoutInflater)
        val catalogActivity = binding.root
        setContentView(catalogActivity)


        val bundle: Bundle? = intent.extras
        val tipo: String? = bundle?.getString("tipo")?.lowercase()
        if (tipo != null) {
            println("El valor del tipo no es nulo $tipo , valor")
            uploadCatalog(tipo)
        }
        else{
            showToast("Error ocurrido al cargar las categorias")
        }
        OnClickBtnHome()
    }

    private fun uploadCatalog(tipo: String) {
        FirebaseFirestore.getInstance().collection("catalog")
            .whereEqualTo("tipo",tipo)
            .get()
            .addOnSuccessListener { result ->
                for (document in result){
                    println("valores $document")
                    val items = result.toObjects( CatalogModel::class.java )
                    val adapter = CatalogAdapter(this, items)
                    binding.catalogMain.layoutManager =  LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    binding.catalogMain.adapter = adapter
                }
            }
            .addOnFailureListener {
                showToast("Error ocurrido: ${it.localizedMessage}")
            }
    }

    fun OnClickBtnHome(){
        binding.btnhome.setOnClickListener {
            val showHome: Intent = Intent(this@CatalogActivity, MainActivity::class.java).apply {  }
            startActivity(showHome)
        }
    }
}
