package com.miracle.products.model.viewModel.product.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

public class ProductDetailsModelFactory(private val productId: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductDetailsViewModel(productId) as T
    }
}