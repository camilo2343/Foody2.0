package com.restaurant.foody20.activity.Modelos

data class CatalogModel (

    val tipo: String,
    val descripcion: String,
    val nombre: String,
    val url: String,
    val valor: Double
    ){
    constructor(): this("","","","",0.0)
}