package com.miracle.products.model.paging.product

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.miracle.products.model.entity.product.Product
import com.miracle.products.model.entity.product.Products
import com.miracle.products.model.repo.product.ProductsApiRepository

class ProductsPagingSource(
    private val productsRepository: ProductsApiRepository
) : PagingSource<Int, Product>() {

    override fun getRefreshKey(state: PagingState<Int, Product>): Int {
        return ((state.anchorPosition ?: 0) - state.config.initialLoadSize / 2).coerceAtLeast(0)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        val  skip = params.key?:0

        var products: Products?

        try {
            products = productsRepository.getProducts(skip = skip, limit = params.loadSize)
        } catch (ex:Exception){
            return LoadResult.Error(ex)
        }

        val maxKey = products.total - params.loadSize

        val nextKey = when {
            products.skip >= maxKey -> null
            else -> products.skip + params.loadSize.coerceAtMost(maxKey)
        }

        return LoadResult.Page(
            prevKey = null,
            nextKey = nextKey,
            data = products.products,
        )
    }
}