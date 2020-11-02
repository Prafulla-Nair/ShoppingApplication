package com.example.shoppingapplication.products.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Products(
    @Expose
    @SerializedName("products")
    val products: MutableList<Product>? = null
)

data class Product(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("price")
    val price: Price? = null,

    @SerializedName("info")
    val info: Info? = null,

    @SerializedName("type")
    var type: String? = null,

    @SerializedName("imageUrl")
    val imageUrl: String? = null
)

data class Price(
    @SerializedName("value")
    val id: Double = 0.0,

    @SerializedName("currency")
    val name: String? = null
)

data class Info(
    @SerializedName("material")
    val material: String? = null,

    @SerializedName("numberOfSeats")
    val numberOfSeats: Int = 0,

    @SerializedName("color")
    val color: String? = null

)
