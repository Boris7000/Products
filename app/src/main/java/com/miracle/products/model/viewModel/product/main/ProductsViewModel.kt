package com.miracle.products.model.viewModel.product.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.miracle.products.model.entity.product.Product
import com.miracle.products.model.repo.product.ProductsApiRepository
import com.miracle.products.model.paging.product.ProductsPagingSource
import kotlinx.coroutines.flow.Flow

open class ProductsViewModel: ViewModel() {
    internal val repository = ProductsApiRepository()

    open val products: Flow<PagingData<Product>> = Pager(
        config = PagingConfig(pageSize = 25, initialLoadSize = 20),
        initialKey = 0,
        pagingSourceFactory = { ProductsPagingSource(repository) }
    ).flow.cachedIn(viewModelScope)
}