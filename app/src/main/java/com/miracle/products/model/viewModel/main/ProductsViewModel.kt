package com.miracle.products.model.viewModel.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.miracle.products.model.product.Product
import com.miracle.products.model.repo.ProductsApiRepository
import com.miracle.products.model.paging.ProductsPagingSource
import kotlinx.coroutines.flow.Flow

open class ProductsViewModel: ViewModel() {
    internal val repository = ProductsApiRepository()

    open val products: Flow<PagingData<Product>> = Pager(
        config = PagingConfig(pageSize = 25, initialLoadSize = 20),
        initialKey = 0,
        pagingSourceFactory = { ProductsPagingSource(repository) }
    ).flow.cachedIn(viewModelScope)
}