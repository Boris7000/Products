package com.miracle.products.model.viewModel.search

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.miracle.products.model.product.Product
import com.miracle.products.model.paging.ProductsSearchPagingSource
import com.miracle.products.model.viewModel.main.ProductsViewModel
import kotlinx.coroutines.flow.Flow

class ProductsSearchViewModel(val query:String): ProductsViewModel() {

    override val products: Flow<PagingData<Product>> = Pager(
        config = PagingConfig(pageSize = 25, initialLoadSize = 20),
        initialKey = 0,
        pagingSourceFactory = { ProductsSearchPagingSource(query, repository) }
    ).flow.cachedIn(viewModelScope)

}