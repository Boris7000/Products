package com.miracle.products.model.viewModel.searchScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ProductsSearchScreenModelFactory (private val query: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductsSearchScreenViewModel(query) as T
    }
}