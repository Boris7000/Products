package com.miracle.products.model.repo.product

import com.miracle.products.model.entity.product.Product
import com.miracle.products.model.entity.product.Products
import com.miracle.products.model.api.RetrofitInstance

class ProductsApiRepository {
    private val productsService = RetrofitInstance.productsService

    suspend fun getProducts(skip:Int?=null, limit:Int?=null): Products {
        return productsService.getProducts(skip, limit)
    }

    suspend fun searchProducts(query:String?=null, skip:Int?=null, limit:Int?=null): Products {
        return productsService.serchProducts(query, skip, limit)
    }

    suspend fun getProduct(id:Int?=null): Product {
        return productsService.getProduct(id)
    }
}