package com.miracle.products.ui.nav

object ProductDetailsDestination:NavigationDestination {

    override val routeDomain: String = "productDetails"
    val idArg:String = "id"

    override val route: String get() = "${routeDomain}/{${idArg}}"

    fun generateDestination(id:Int):String{
        return "${routeDomain}/${id}"
    }

}