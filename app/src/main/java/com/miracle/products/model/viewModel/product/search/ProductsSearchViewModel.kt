package com.miracle.products.model.viewModel.product.search

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.miracle.products.model.entity.product.Product
import com.miracle.products.model.paging.product.ProductsSearchPagingSource
import com.miracle.products.model.viewModel.product.main.ProductsViewModel
import kotlinx.coroutines.flow.Flow

class ProductsSearchViewModel(val query:String): ProductsViewModel() {

    override val products: Flow<PagingData<Product>> = Pager(
        config = PagingConfig(pageSize = 25, initialLoadSize = 20),
        initialKey = 0,
        pagingSourceFactory = { ProductsSearchPagingSource(query, repository) }
    ).flow.cachedIn(viewModelScope)

}