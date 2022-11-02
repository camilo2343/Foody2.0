package com.restaurant.foody20.activity.Information
import com.restaurant.foody20.R
import com.restaurant.foody20.activity.Modelos.PopularModel

object PopulateData {
    val dataPopulate = listOf<PopularModel>(
        PopularModel(
            "Pizza de peperoni",
            R.drawable.pop_1,
            "Pizza de peperoni, con queso mozarella y oregano fresco",
            9.000,
            1
        ),
        PopularModel(
            "Hamburguesa de queso",
            R.drawable.pop_2,
            "Hamburguesa de carne con lechuga, tomaes frescos y salsa de la casa",
            15.000,
            2
        ),
        PopularModel(
            "Pizza de vegetales",
            R.drawable.pop_3,
            "Pizza de vegetales, aceite de oliva, aceite vegetal, tomate cherry y oregano fresco",
            12.000,
            3
        )
    )

}