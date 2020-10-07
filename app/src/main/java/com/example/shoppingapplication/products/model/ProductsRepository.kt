package com.example.shoppingapplication.products.model

import android.content.Context
import android.util.Log
import com.example.shoppingapplication.R
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

//Get data
class ProductsRepository @Inject constructor(private val context: Context) {

    private var products: Products? = null

    /**parse JSON response
     * @return Products object
     */
    suspend fun parseJSON(): Products? =
        withContext(Dispatchers.IO) {
            products =
                Gson().fromJson(readJSONFromResources(), Products::class.java)
            Log.d("Test","products - "+products)
            return@withContext products
        }

    /**Read JSON from resources folder
     * @return JSON String value
     */
    private fun readJSONFromResources(): String? {
        return context.resources.openRawResource(R.raw.products)
            .bufferedReader().use {
                it.readText()
            }
    }


}