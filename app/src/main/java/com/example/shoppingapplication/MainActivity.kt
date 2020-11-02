package com.example.shoppingapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppingapplication.cart.view.ShoppingCartFragment
import com.example.shoppingapplication.products.model.Product
import com.example.shoppingapplication.products.view.ProductsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_holder, ProductsFragment())
            .commit()
    }

    fun onCartIconClicked(cartItems: HashMap<Product, Int>) {
        val args = Bundle()
        args.putSerializable("cartItems", cartItems)
        val fragment = ShoppingCartFragment()
        fragment.arguments = args
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_holder, fragment)
            .addToBackStack(ShoppingCartFragment::class.java.simpleName)
            .commit()
    }
}
