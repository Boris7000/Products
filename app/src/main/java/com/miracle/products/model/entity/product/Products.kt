package com.miracle.products.model.entity.product

data class Products(
    val products:MutableList<Product>,
    val total:Int,
    val skip:Int,
    val limit:Int
)
