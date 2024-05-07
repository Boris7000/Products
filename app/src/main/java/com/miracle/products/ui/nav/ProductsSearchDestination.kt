package com.miracle.products.ui.nav

object ProductsSearchDestination:NavigationDestination {

    override val routeDomain: String = "products"
    val queryArg:String = "query"

    override val route: String get() = "${routeDomain}?${queryArg}={${queryArg}}"

    fun generateDestination(query: String):String{
        return "${routeDomain}?${queryArg}=${query}"
    }

}