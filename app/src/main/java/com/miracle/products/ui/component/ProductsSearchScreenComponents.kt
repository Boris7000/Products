package com.miracle.products.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import com.miracle.products.R
import com.miracle.products.model.viewModel.searchScreen.ProductsSearchScreenModelFactory
import com.miracle.products.model.viewModel.searchScreen.ProductsSearchScreenViewModel
import com.miracle.products.ui.nav.ProductsSearchDestination

object ProductsSearchScreenComponents {

    @Composable
    public fun ProductsSearchScreenModelWrapper(
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

        val viewModel: ProductsSearchScreenViewModel = viewModel(
            viewModelStoreOwner = viewModelStoreOwner,
            factory = ProductsSearchScreenModelFactory(validatedQuery)
        )

        val query by viewModel.query.observeAsState(validatedQuery)

        ProductsSearchScreen(
            modifier = modifier,
            query = query,
            onQueryChange = { newQuery->
                viewModel.setQuery(newQuery)
            },
            navigateBack = navigateBack,
            showBackButton = showBackButton,
            onRoute = onRoute
        )
    }

    @Composable
    public fun ProductsSearchScreen(modifier: Modifier = Modifier,
                                    query: String,
                                    onQueryChange: (query: String) -> Unit = {},
                                    navigateBack: () -> Unit = {},
                                    showBackButton: Boolean = false,
                                    onRoute:(
                                        (destination: String,
                                         navOptions: NavOptions?,
                                         navigatorExtras: Navigator.Extras?)->Unit)?=null
    ){
        Scaffold(
            modifier = modifier,
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
            topBar = {
                ProductsSearchAppBar(
                    modifier = Modifier.fillMaxWidth(),
                    query = query,
                    onQueryChange = onQueryChange,
                    onForceSearch = {query->
                        onRoute?.invoke(ProductsSearchDestination.generateDestination(query),
                            null, null)
                    },
                    navigateBack = navigateBack,
                    showBackButton = showBackButton
                )
            }
        ){ paddingValues->
            paddingValues.let {  }
        }
    }

    @Composable
    private fun ProductsSearchAppBar(
        modifier: Modifier = Modifier,
        query: String,
        onQueryChange: (query: String) -> Unit = {},
        onForceSearch: (query:String) -> Unit = {},
        navigateBack: () -> Unit = {},
        showBackButton: Boolean = false,
    ) {

        val focusRequester = remember { FocusRequester() }

        SimpleSearchBar(
            modifier = modifier
                .padding(8.dp)
                .focusRequester(focusRequester),
            leadingIcon = {
                if (showBackButton){
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                } else {
                    IconButton(onClick = {onForceSearch(query)}) {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = stringResource(R.string.search)
                        )
                    }
                }
            },
            placeholder = {
                Text(stringResource(R.string.searchInProducts))
            },
            trailingIcon = {
                if (showBackButton){
                    IconButton(onClick = {onForceSearch(query)}) {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = stringResource(R.string.search)
                        )
                    }
                }
            },
            query = query,
            onQueryChange = onQueryChange,
            onSearch = onForceSearch
        )

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

    }

}