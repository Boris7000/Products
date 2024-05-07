package com.miracle.products.ui.nav

object ProductsSearchScreenDestination:NavigationDestination {

    override val routeDomain: String = "productsSearchScreen"
    val queryArg:String = "query"

    override val route: String get() = "${routeDomain}?${queryArg}={${queryArg}}"

    fun generateDestination(query: String=""):String{
        return "${routeDomain}?${queryArg}=${query}"
    }

}