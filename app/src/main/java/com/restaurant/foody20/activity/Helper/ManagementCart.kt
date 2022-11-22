package com.restaurant.foody20.activity.Helper

import android.content.Context
import com.restaurant.foody20.activity.Modelos.PopularModel
import android.widget.Toast
import com.restaurant.foody20.Interface.ChangeNumberItemsListener
import java.io.Serializable
import java.util.ArrayList

class ManagementCart(private val context: Context?) {
    private val tinyDB: TinyDB

    init {
        tinyDB = TinyDB(context)
    }

    fun insertFood(item: Serializable) {
        val listFood = listCart
        var existAlready = false
        var n = 0
        for (i in listFood.indices) {
            if (listFood[i].title == item.javaClass) {
                existAlready = true
                n = i
                break
            }
            if (existAlready) {
                listFood[n].setNumberInCart()
            } else {
                listFood.add()
            }
        }
        tinyDB.putListObject("CartList", listFood)
        Toast.makeText(context, "Added To your Cart", Toast.LENGTH_SHORT).show()
    }

    val listCart: ArrayList<PopularModel>
        get() = tinyDB.getListObject("CartList")

    fun plusNumberFood(
        listFood: PopularModel.CREATOR,
        position: Int,
        changeNumberItemsListener: ChangeNumberItemsListener
    ) {
        listFood[position].setNumberInCart(listFood[position].numberInCart + 1)
        tinyDB.putListObject("CartList", listFood)
        changeNumberItemsListener.changed()
    }

    fun minusNumberFood(
        listfood: PopularModel.CREATOR,
        position: Int,
        changeNumberItemsListener: ChangeNumberItemsListener
    ) {
        if (listfood[position].numberInCart == 1) {
            listfood.removeAt(position)
        } else {
            listfood[position].setNumberInCart(listfood[position].numberInCart - 1)
        }
        tinyDB.putListObject("CartList", listfood)
        changeNumberItemsListener.changed()
    }

    val totalCost: Double
        get() {
            val listfood = listCart
            var cost = 0.0
            for (i in listfood.indices) {
                cost = cost + listfood[i].cost * listfood[i].numberInCart
            }
            return cost
        }
}