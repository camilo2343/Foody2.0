package com.restaurant.foody20.activity.Modelos

data class PopularModel(val title:String, val pic: Int, val description:String, val cost: Double, val numberCard: Int) {

    constructor(): this(
        "",
        0,
        "",
        0.0,
        0
    )
}