package com.example.shoppingapplication.products.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Products(
    @Expose
    @SerializedName("products")
    var products: MutableList<Product>? = null
)


data class Product(

    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("price")
    var price: Price? = null,

    @SerializedName("info")
    var info: Info? = null,

    @SerializedName("type")
    var type: String? = null,

    @SerializedName("imageUrl")
    var imageUrl: String? = null)

data class Price(
    @SerializedName("value")
    var id: Double = 0.0,

    @SerializedName("currency")
    var name: String? = null
)

data class Info(
    @SerializedName("material")
    var material: String? = null,

    @SerializedName("numberOfSeats")
    var numberOfSeats: Int = 0,

    @SerializedName("color")
    var color: String? = null


)
