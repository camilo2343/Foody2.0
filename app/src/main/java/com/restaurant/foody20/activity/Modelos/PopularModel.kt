package com.restaurant.foody20.activity.Modelos

import android.os.Parcel
import android.os.Parcelable


data class PopularModel(val title:String, val pic: Int, val description:String, val cost: Double, val numberInCart: Int) {

    constructor(): this(
        "",
        0,
        "",
        0.0,
        0
    )

    }



