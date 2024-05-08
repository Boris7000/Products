package com.miracle.products

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.miracle.products.model.viewModel.details.ProductDetailsModelFactory
import com.miracle.products.model.viewModel.details.ProductDetailsViewModel
import com.miracle.products.model.viewModel.search.ProductSearchModelFactory
import com.miracle.products.model.viewModel.search.ProductsSearchViewModel
import com.miracle.products.model.viewModel.main.ProductsViewModel
import com.miracle.products.ui.component.ProductDetailsComponents.ProductDetailsModelWrapper
import com.miracle.products.ui.component.ProductsComponents.ProductsModelWrapper
import com.miracle.products.ui.component.ProductsSearchComponents.ProductsSearchModelWrapper
import com.miracle.products.ui.component.ProductsSearchScreenComponents.ProductsSearchScreenModelWrapper
import com.miracle.products.ui.nav.DestinationRoute.PRODUCTS
import com.miracle.products.ui.nav.ProductDetailsDestination
import com.miracle.products.ui.nav.ProductsSearchDestination
import com.miracle.products.ui.nav.ProductsSearchScreenDestination
import com.miracle.products.ui.theme.ProductsTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()

            ProductsTheme {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surfaceContainerLowest),
                    ){
                    NavHost(navController = navController,
                        startDestination = PRODUCTS){

                        composable(PRODUCTS){
                            ProductsModelWrapper(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .safeDrawingPadding(),
                                viewModelStoreOwner = it,
                                navigateBack = navController::popBackStack,
                                showBackButton = true,
                                onRoute = navController::navigate
                            )
                        }

                        composable(
                            route = ProductDetailsDestination.route,
                            arguments = listOf(navArgument(ProductDetailsDestination.idArg) {
                                type = NavType.IntType
                            })
                        ){
                            val id = it.arguments?.getInt(ProductDetailsDestination.idArg)
                            ProductDetailsModelWrapper(
                                modifier = Modifier
                                    .fillMaxSize(),
                                //.safeDrawingPadding(),
                                viewModelStoreOwner = it,
                                idArg = id,
                                navigateBack = navController::popBackStack,
                                showBackButton = true,
                                onRoute = navController::navigate
                            )
                        }

                        composable(route = ProductsSearchDestination.route,
                            arguments = listOf(navArgument(ProductsSearchDestination.queryArg) {
                                type = NavType.StringType
                            })){
                            val query = it.arguments?.getString(ProductsSearchScreenDestination.queryArg)
                            ProductsSearchModelWrapper(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .safeDrawingPadding(),
                                viewModelStoreOwner = it,
                                queryArg = query,
                                navigateBack = navController::popBackStack,
                                showBackButton = true,
                                onRoute = navController::navigate
                            )
                        }

                        composable(route = ProductsSearchScreenDestination.route,
                            arguments = listOf(navArgument(ProductsSearchScreenDestination.queryArg) {
                                type = NavType.StringType
                                nullable = true
                            })){
                            val query = it.arguments?.getString(ProductsSearchScreenDestination.queryArg)
                            ProductsSearchScreenModelWrapper(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .safeDrawingPadding(),
                                viewModelStoreOwner = it,
                                queryArg = query,
                                navigateBack = navController::popBackStack,
                                showBackButton = true,
                                onRoute = navController::navigate
                            )
                        }
                    }
                }
            }
        }
    }
}


