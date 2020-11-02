package com.example.shoppingapplication.products.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoppingapplication.products.model.ProductsRepository
import javax.inject.Inject

class ProductsViewModelProvider @Inject constructor(private val productsRepository: ProductsRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(ViewModel: Class<T>): T {
        return ViewModel.cast(ProductsViewModel(productsRepository))!!
    }
}
