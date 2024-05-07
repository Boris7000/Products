package com.miracle.products.model.viewModel.product.searchScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.miracle.products.model.viewModel.product.main.ProductsViewModel

class ProductsSearchScreenViewModel(query:String): ProductsViewModel() {

    private val _query = MutableLiveData<String>(query)
    val query: LiveData<String> = _query

    fun setQuery(query: String) {
        _query.value = query
    }

}