package com.restaurant.foody20

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.restaurant.foody20.Interface.ChangeNumberItemsListener
import com.restaurant.foody20.activity.Adaptador.CartListAdapter
import com.restaurant.foody20.activity.Helper.ManagementCart
import com.restaurant.foody20.activity.MainActivity

class CartListActivity : AppCompatActivity() {
    private var adapter: RecyclerView.Adapter<*>? = null
    private var recyclerViewList: RecyclerView? = null
    private var managementCart: ManagementCart? = null
    var totalCostTxt: TextView? = null
    var taxTxt: TextView? = null
    var deliveryTxt: TextView? = null
    var emptyTxt: TextView? = null
    var totalTxt: TextView? = null
    private var tax = 0.0
    private var scrollView: ScrollView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_list)
        managementCart = ManagementCart(this)
        initView()
        initList()
        CalculateCart()
        bottomNavigation()
    }

    private fun bottomNavigation() {
        val floatingActionButton = findViewById<FloatingActionButton>(R.id.cartBtn)
        val homeBtn = findViewById<LinearLayout>(R.id.btnhome)
        floatingActionButton.setOnClickListener {
            startActivity(
                Intent(
                    this@CartListActivity,
                    CartListActivity::class.java
                )
            )
        }
        homeBtn.setOnClickListener {
            startActivity(
                Intent(
                    this@CartListActivity,
                    MainActivity::class.java
                )
            )
        }
    }

    private fun initView() {
        recyclerViewList = findViewById(R.id.recyclerViewCat)
        totalCostTxt = findViewById(R.id.totalCostTxt)
        taxTxt = findViewById(R.id.taxTxt)
        deliveryTxt = findViewById(R.id.deliveryTxt)
        totalTxt = findViewById(R.id.totalTxt)
        emptyTxt = findViewById(R.id.emptyTxt)
        scrollView = findViewById(R.id.scrollView2)
        recyclerViewList = findViewById(R.id.recyclerViewCat)
    }

    private fun initList() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewList!!.layoutManager = linearLayoutManager
        adapter =
            CartListAdapter(managementCart!!.listCart, this, object : ChangeNumberItemsListener {
                override fun changed() {
                    CalculateCart()
                }
            })
        recyclerViewList!!.adapter = adapter
        if (managementCart!!.listCart.isEmpty()) {
            emptyTxt!!.visibility = View.VISIBLE
            scrollView!!.visibility = View.GONE
        } else {
            emptyTxt!!.visibility = View.GONE
            scrollView!!.visibility = View.VISIBLE
        }
    }

    private fun CalculateCart() {
        val percentTax = 0.02
        val deliver = 10.0
        tax = (Math.round(managementCart!!.totalCost * percentTax) * 100).toDouble()
        100
        val total = (Math.round(managementCart!!.totalCost + tax + deliver) * 100).toDouble()
        100
        val itemTotal = (Math.round(managementCart!!.totalCost * 100) / 100).toDouble()
        totalCostTxt!!.text = "$itemTotal"
        taxTxt!!.text = "$tax"
        deliveryTxt!!.text = "$deliver"
        totalTxt!!.text = "$total"
    }
}