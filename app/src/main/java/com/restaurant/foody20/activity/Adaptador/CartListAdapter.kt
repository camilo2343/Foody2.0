package com.restaurant.foody20.activity.Adaptador

import android.content.Context
import com.restaurant.foody20.activity.Modelos.PopularModel


import com.restaurant.foody20.Interface.ChangeNumberItemsListener
import androidx.recyclerview.widget.RecyclerView
import com.restaurant.foody20.activity.Helper.ManagementCart
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.restaurant.foody20.R
import com.bumptech.glide.Glide
import android.widget.TextView
import java.util.ArrayList

class CartListAdapter(
    private val popularModels: ArrayList<PopularModel>,
    context: Context?,
    changeNumberItemsListener: ChangeNumberItemsListener
) : RecyclerView.Adapter<CartListAdapter.ViewHolder>() {
    private val managementCart: ManagementCart
    private val changeNumberItemsListener: ChangeNumberItemsListener

    init {
        managementCart = ManagementCart(context)
        this.changeNumberItemsListener = changeNumberItemsListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate =
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_catalog, parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title!!.text = popularModels[position].toString()
        holder.costEachItem.text = popularModels[position].cost.toString()
        holder.totalEachItem.setText((Math.round(popularModels[position].numberInCart * popularModels[position].cost) * 100)/100.toString())
        holder.num.text = popularModels[position].numberInCart.toString()
        val drawableResourceId = holder.itemView.context.resources.getIdentifier(
            popularModels[position].pic.toString(),
            "drawable", holder.itemView.context.packageName
        )
        Glide.with(holder.itemView.context)
            .load(drawableResourceId)
            .into(holder.pic)
        holder.plusItem.setOnClickListener {
            managementCart.plusNumberFood(PopularModel, position,
                object : ChangeNumberItemsListener {
                    override fun changed() {
                        notifyDataSetChanged()
                        changeNumberItemsListener.changed()
                    }
                })
        }
        holder.minusItem.setOnClickListener {
            managementCart.minusNumberFood(PopularModel, position,
                object : ChangeNumberItemsListener {
                    override fun changed() {
                        notifyDataSetChanged()
                        changeNumberItemsListener.changed()
                    }
                })
        }
    }

    override fun getItemCount(): Int {
        return PopularModel.size()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView
        var costEachItem: TextView
        var pic: ImageView
        var plusItem: ImageView
        var minusItem: ImageView
        var totalEachItem: TextView
        var num: TextView

        init {
            title = itemView.findViewById(R.id.titleTxt)
            costEachItem = itemView.findViewById(R.id.valorCat)
            pic = itemView.findViewById(R.id.itemCat)
            totalEachItem = itemView.findViewById(R.id.cantidad)
            num = itemView.findViewById(R.id.cost)
            plusItem = itemView.findViewById(R.id.mas)
            minusItem = itemView.findViewById(R.id.menos)
        }
    }
}

private fun Any.getTitle(): Any {

}
