package com.miracle.products.model.viewModel.product.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miracle.products.model.entity.product.Product
import com.miracle.products.model.repo.product.ProductsApiRepository
import kotlinx.coroutines.launch

class ProductDetailsViewModel(val id:Int): ViewModel() {

    private val repository = ProductsApiRepository()

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product

    fun fetchProduct() {
        Log.d("frofr2",id.toString())
        viewModelScope.launch {
            try {
                val prod = repository.getProduct(id)
                _product.value = prod
            } catch (e: Exception) {
                Log.d("Repo", "fetchProduct exception ${e.message}")
            }
        }
    }

    override fun onCleared() {
        Log.d("frofr2","cleared")
        super.onCleared()
    }
}