package com.example.shoppingapplication.di

import android.content.Context
import com.example.shoppingapplication.cart.view.ShoppingCartFragment
import com.example.shoppingapplication.products.view.ProductsFragment
import dagger.BindsInstance
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(fragment: ProductsFragment)

    fun inject(fragment: ShoppingCartFragment)
}
