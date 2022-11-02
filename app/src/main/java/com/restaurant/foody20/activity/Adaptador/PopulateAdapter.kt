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
import com.restaurant.foody20.activity.Modelos.PopularModel

class PopulateAdapter(private val context: Context, private val populate: List<PopularModel>):RecyclerView.Adapter<PopulateAdapter.PopulateViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopulateViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_populate,parent,false)
        return PopulateViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PopulateViewHolder, position: Int) {
        val varItem = populate[position]

        holder.title.text = varItem.title
        holder.pic.setImageResource(varItem.pic)
        holder.cost.text = varItem.cost.toString()
    }

    override fun getItemCount(): Int {
        return populate.size
    }

    class PopulateViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val title:TextView = itemView.findViewById(R.id.title)
        val pic: ImageView = itemView.findViewById(R.id.pic)
        val cost: TextView = itemView.findViewById(R.id.cost)
    }

}

