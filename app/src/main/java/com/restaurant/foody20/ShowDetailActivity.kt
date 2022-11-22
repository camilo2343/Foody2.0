package com.restaurant.foody20

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.restaurant.foody20.activity.Helper.ManagementCart
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.Serializable
import java.lang.String

class ShowDetailActivity : AppCompatActivity() {
    private var addToCartBtn: TextView? = null
    private var tittleTxt: TextView? = null
    private var costTxt: TextView? = null
    private var descriptionTxt: TextView? = null
    private var numberOrderTxt: TextView? = null
    private var plusBtn: ImageView? = null
    private var minusBtn: ImageView? = null
    private var picFood: ImageView? = null
    private var `object`: Serializable? = null
    private var managementCart: ManagementCart? = null
    var numberOrder = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_detail)
        managementCart = ManagementCart(this)
        initView()
        bundle
    }

    private val bundle: Unit
        private get() {
            `object` = intent.getSerializableExtra("object")
            val drawableResourceId =
                this.resources.getIdentifier(`object`.getPic(), "drawable", this.packageName)
            Glide.with(this)
                .load(drawableResourceId)
                .into(picFood!!)
            tittleTxt.setText(`object`.getTittle())
            costTxt!!.text = "$" + Object.getCost()
            descriptionTxt.setText(`object`.getDescription())
            numberOrderTxt.setText(String.valueOf(numerOrder))
            plusBtn!!.setOnClickListener {
                numberOrder = numberOrder + 1
                numberOrderTxt!!.text = numberOrder.toString()
            }
            minusBtn!!.setOnClickListener {
                if (numberOrder > 1) {
                    numberOrder = numberOrder - 1
                }
                numberOrderTxt!!.text = numberOrder.toString()
            }
            addToCartBtn!!.setOnClickListener {
                `object`.setNumberInCart(numberOrder)
                managementCart!!.insertFood(`object`!!)
            }
        }

    private fun initView() {
        addToCartBtn = findViewById(R.id.addToCartBtn)
        tittleTxt = findViewById(R.id.titleTxt)
        costTxt = findViewById(R.id.cost)
        descriptionTxt = findViewById(R.id.descriptionTxt)
        numberOrderTxt = findViewById(R.id.numberOrderTxt)
        plusBtn = findViewById(R.id.plusBtn)
        minusBtn = findViewById(R.id.minusBtn)
        picFood = findViewById(R.id.picfood)
    }
}