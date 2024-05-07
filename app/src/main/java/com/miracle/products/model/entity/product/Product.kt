package com.miracle.products.model.entity.product

import androidx.compose.runtime.Immutable

@Immutable
data class Product constructor(
    val id:Int,
    val title:String,
    val description:String="",
    val price:Int,
    val discountPercentage:Float=0f,
    val rating:Float=0f,
    val stock:Int=0,
    val brand:String,
    val category:String,
    val thumbnail:String,
    val images:List<String> = listOf(thumbnail)
) {

    private var _discountPrice:Int = 0
    val discountPrice:Int get(){
        return if(_discountPrice>0){
            _discountPrice
        } else {
            if (discountPercentage>0) {
                 (price - (price * discountPercentage / 100).toInt()).also {_discountPrice=it}
            } else {
                price.also { _discountPrice=it }
            }
        }
    }
}