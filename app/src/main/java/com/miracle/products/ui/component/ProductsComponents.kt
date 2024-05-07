package com.miracle.products.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.navOptions
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.miracle.products.R
import com.miracle.products.model.ItemContentTypes
import com.miracle.products.model.entity.product.Product
import com.miracle.products.model.viewModel.product.main.ProductsViewModel
import com.miracle.products.ui.nav.ProductDetailsDestination
import com.miracle.products.ui.nav.ProductsSearchScreenDestination
import com.miracle.products.ui.theme.ProductsTheme

object ProductsComponents {

    @Composable
    public fun ProductsModelWrapper(
        modifier: Modifier = Modifier,
        viewModelStoreOwner: ViewModelStoreOwner,
        navigateBack: () -> Unit = {},
        showBackButton: Boolean = false,
        onRoute:(
            (destination: String,
             navOptions: NavOptions?,
             navigatorExtras: Navigator.Extras?)->Unit)?=null
    ){

        val viewModel: ProductsViewModel = viewModel(
            viewModelStoreOwner = viewModelStoreOwner)

        val products: LazyPagingItems<Product> = viewModel.products.collectAsLazyPagingItems()

        Products(
            modifier = modifier,
            products = products,
            navigateBack = navigateBack,
            showBackButton = showBackButton,
            onRoute = onRoute
        )

    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun Products(
        modifier: Modifier = Modifier,
        query: String = "",
        products: LazyPagingItems<Product>,
        navigateBack: () -> Unit = {},
        showBackButton: Boolean = false,
        onRoute:(
            (destination: String,
             navOptions: NavOptions?,
             navigatorExtras: Navigator.Extras?)->Unit)?=null
    ){

        val pullRefreshState = rememberPullRefreshState(
            refreshing = products.loadState.refresh==LoadState.Loading,
            onRefresh = { products.refresh() }
        )

        Scaffold(
            modifier = modifier,
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
            topBar = {
                ProductsAppBar(
                    modifier = Modifier.fillMaxWidth(),
                    searchText = query,
                    navigateBack = navigateBack,
                    showBackButton = showBackButton,
                    onForceSearch = {
                        onRoute?.invoke(ProductsSearchScreenDestination.generateDestination(query),
                            null, null)
                    }
                )
            }
        ){ paddingValues->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues)
                    .pullRefresh(pullRefreshState)
            ){
                when(products.loadState.refresh){
                    is LoadState.Error ->{
                        val loadStateError: LoadState.Error = products.loadState.refresh as LoadState.Error
                        Column(modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())) {
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)){
                                ErrorMessage(
                                    modifier = Modifier.align(Alignment.Center),
                                    message = loadStateError.error.message,
                                    onRertry = {products.retry()}
                                )
                            }
                        }
                    }
                    is LoadState.NotLoading->{
                        ProductList(
                            products = products,
                            modifier = Modifier.fillMaxSize(),
                            onShowDetails = { id->
                                onRoute?.invoke(ProductDetailsDestination.generateDestination(id),
                                    null, null)
                            }
                        )
                    }
                    else -> Unit
                }

                PullRefreshIndicator(
                    refreshing = products.loadState.refresh== LoadState.Loading,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter),
                    contentColor = if (products.loadState.refresh== LoadState.Loading)
                        MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.secondary,
                )
            }
        }
    }

    @Composable
    fun ProductList(
        modifier: Modifier = Modifier,
        products: LazyPagingItems<Product>,
        onShowDetails:((id: Int)->Unit)?=null
    ) {
        LazyVerticalStaggeredGrid(
            modifier = modifier,
            columns = StaggeredGridCells.Fixed(2),
            verticalItemSpacing = 8.dp,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(
                count = products.itemCount,
                key = products.itemKey { it.id },
                contentType = products.itemContentType {
                    ItemContentTypes.ProductCard
                }
            ){ index ->
                products[index]?.let { product ->
                    ProductCard(
                        modifier = Modifier
                            .fillMaxWidth(),
                        product = product,
                        onShowDetails = onShowDetails
                    )
                }
            }

            item(span = StaggeredGridItemSpan.FullLine) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)){

                    when (products.loadState.append) {
                        is LoadState.NotLoading -> Unit
                        is LoadState.Loading -> {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(30.dp)
                                    .align(Alignment.Center),
                                color = MaterialTheme.colorScheme.primary,
                            )
                        }
                        is LoadState.Error -> {
                            val loadStateError: LoadState.Error = products.loadState.append as LoadState.Error
                            ErrorMessage(
                                modifier = Modifier.align(Alignment.Center),
                                message = loadStateError.error.message,
                                onRertry = {products.retry()}
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun ProductCard(
        modifier: Modifier = Modifier,
        product: Product,
        onShowDetails:((id: Int)->Unit)?=null
    ) {

        val discountPrice:Int = remember(product.discountPercentage, product.price) {
            product.discountPrice
        }

        Card(
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceContainer),
            modifier = modifier
        ) {
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                ImagesCarousel(
                    images = product.images,
                    modifier = Modifier.fillMaxWidth()
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(text = "${product.title}",
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis)

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        if (product.discountPercentage>0) {
                            Text(text = "${discountPrice}\$",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium)

                            Text(text = "${product.price}\$",
                                style = TextStyle(textDecoration = TextDecoration.LineThrough),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium)

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(7.dp))
                                    .background(MaterialTheme.colorScheme.primary)
                            ) {
                                Text(text = "-${product.discountPercentage.toInt()}%",
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(4.dp,0.dp))
                            }
                        } else {
                            Text(text = "${product.price}",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium)
                        }
                    }

                    Text(text = if (product.stock>0)
                        stringResource(R.string.inStock,product.stock)
                    else stringResource(R.string.outOfStock),
                        fontSize = 14.sp)

                    if (product.rating>0){
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(android.R.drawable.star_on),
                                tint = MaterialTheme.colorScheme.tertiary,
                                contentDescription = stringResource(R.string.rating)
                            )
                            Text(text = "${product.rating}",
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.tertiary,
                                fontSize = 15.sp
                            )
                        }
                    }
                }

                if(onShowDetails!=null){
                    Button(
                        onClick = {onShowDetails.invoke(product.id)},
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = stringResource(R.string.details),
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun ProductsAppBar(
        modifier: Modifier = Modifier,
        searchText: String = "",
        onForceSearch: () -> Unit = {},
        navigateBack: () -> Unit = {},
        showBackButton: Boolean = false,
    ) {

        val placeholderText = if(searchText.isNotEmpty()){
            searchText
        } else {
            stringResource(R.string.searchInProducts)
        }

        SearchButton(
            modifier = modifier
                .padding(8.dp),
            leadingIcon = {
                if (showBackButton){
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                } else {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = stringResource(R.string.search)
                    )
                }
            },
            placeholder = {
                Text(text = placeholderText)
            },
            trailingIcon = {
                if (showBackButton){
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = stringResource(R.string.search)
                    )
                }
            },
            onClick = onForceSearch
        )
    }


    @Composable
    fun ProductsSearchButton(
        modifier: Modifier = Modifier,
        searchText: String = stringResource(R.string.searchInProducts),
        onForceSearch: () -> Unit = {}
    ){
        SearchButton(
            modifier = modifier
                .padding(8.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = stringResource(R.string.search)
                )
            },
            placeholder = {
                Text(text = searchText,
                    fontSize = 16.sp)
            },
            onClick = onForceSearch
        )
    }
}