package com.miracle.products.model.viewModel.product.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ProductSearchModelFactory (private val query: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductsSearchViewModel(query) as T
    }
}