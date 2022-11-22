package com.restaurant.foody20.activity.Adaptador

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.restaurant.foody20.R
import com.restaurant.foody20.activity.Modelos.CatalogModel

class CatalogAdapter(private val context: Context, private val catalogModel: MutableList<CatalogModel>): RecyclerView.Adapter<CatalogViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        return CatalogViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.viewholder_catalog, parent,false )
        )
    }

    override fun getItemCount(): Int {
        return catalogModel.size
    }

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        val catalog = catalogModel[position]
        holder.nombreCat.text = catalog.nombre
        holder.descripcionCat.text = catalog.description
        holder.valorCat.text = catalog.valor.toString()
        Glide.with(context)
            .load(catalog.url)
            .into(holder.urlCat)
    }

}

class CatalogViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    val nombreCat: TextView = itemView.findViewById(R.id.nombreCat)
    val descripcionCat: TextView = itemView.findViewById(R.id.descripcionCat)
    val valorCat: TextView = itemView.findViewById(R.id.valorCat)
    val urlCat : ImageView = itemView.findViewById(R.id.itemCat)
}