package com.restaurant.foody20.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.restaurant.foody20.R
import com.restaurant.foody20.activity.Modelos.CategoryModel


class CategoryAdapter(private val context: Context, private val categories: List<CategoryModel>):
    RecyclerView.Adapter<CategoryViewHolder>() {

    var onItemClick: ((CategoryModel) -> Unit)? = null

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
    return CategoryViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.viewolder_category, parent, false)
    )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.type.text = category.type

        // Código para Firebase
        Glide.with(context)
            .load(category.url)
            .into(holder.url)
        println ("valores de categorias ${category.type}")
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(category)
        }
    }
}


class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val type: TextView = itemView.findViewById(R.id.categoryName)
        val url: ImageView = itemView.findViewById(R.id.categoryPic)
    }









