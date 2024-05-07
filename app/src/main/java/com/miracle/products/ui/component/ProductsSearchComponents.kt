package com.miracle.products.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.miracle.products.model.entity.product.Product
import com.miracle.products.model.viewModel.product.search.ProductSearchModelFactory
import com.miracle.products.model.viewModel.product.search.ProductsSearchViewModel
import com.miracle.products.ui.component.ProductsComponents.Products

object ProductsSearchComponents {

    @Composable
    public fun ProductsSearchModelWrapper(
        modifier: Modifier = Modifier,
        viewModelStoreOwner: ViewModelStoreOwner,
        queryArg: String? = null,
        navigateBack: () -> Unit = {},
        showBackButton: Boolean = false,
        onRoute:(
            (destination: String,
             navOptions: NavOptions?,
             navigatorExtras: Navigator.Extras?)->Unit)?=null
    ){

        val validatedQuery = queryArg?:""

        val viewModel: ProductsSearchViewModel = viewModel(
            viewModelStoreOwner = viewModelStoreOwner,
            factory = ProductSearchModelFactory(validatedQuery)
        )

        val products: LazyPagingItems<Product> = viewModel.products.collectAsLazyPagingItems()

        Products(
            modifier = modifier,
            query = validatedQuery,
            products = products,
            navigateBack = navigateBack,
            showBackButton = showBackButton,
            onRoute = onRoute
        )
    }
}