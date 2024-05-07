package com.miracle.products.model.api

import com.miracle.products.model.api.product.ProductsService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://dummyjson.com/"
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val productsService: ProductsService by lazy {
        retrofit.create(ProductsService::class.java)
    }
}