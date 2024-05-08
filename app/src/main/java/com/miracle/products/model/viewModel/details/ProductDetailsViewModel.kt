package com.miracle.products.model.viewModel.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miracle.products.model.product.Product
import com.miracle.products.model.repo.ProductsApiRepository
import com.miracle.products.model.viewModel.loader.LoadState
import kotlinx.coroutines.launch

class ProductDetailsViewModel(val id:Int): ViewModel() {

    private val _loadingState = MutableLiveData<LoadState>()
    val loadState: LiveData<LoadState> get() = _loadingState

    private val repository = ProductsApiRepository()

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product

    fun fetchProduct() {
        viewModelScope.launch {
            _loadingState.value = LoadState.Loading(_product.value != null)
            try {
                val prod = repository.getProduct(id)
                _product.value = prod
                _loadingState.value = LoadState.NotLoading(true)
            } catch (e: Exception) {
                _loadingState.value = LoadState.Error(e,_product.value!=null)
            }
        }
    }
}