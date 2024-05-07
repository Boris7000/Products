package com.miracle.products.model.api.product

import com.miracle.products.model.entity.product.Product
import com.miracle.products.model.entity.product.Products
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductsService {
    @GET("products") suspend fun getProducts(
        @Query("skip") skip:Int?=null,
        @Query("limit") limit:Int?=null): Products

    @GET("products/{id}") suspend fun getProduct(
        @Path("id") skip:Int?=null): Product

    @GET("products/search") suspend fun serchProducts(
        @Query("q") q:String?=null,
        @Query("skip") skip:Int?=null,
        @Query("limit") limit:Int?=null): Products

}