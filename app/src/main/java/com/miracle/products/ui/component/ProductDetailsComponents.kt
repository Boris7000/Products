package com.miracle.products.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import com.miracle.products.R
import com.miracle.products.model.product.Product
import com.miracle.products.model.viewModel.loader.LoadState
import com.miracle.products.model.viewModel.details.ProductDetailsModelFactory
import com.miracle.products.model.viewModel.details.ProductDetailsViewModel

object ProductDetailsComponents {

    @Composable
    public fun ProductDetailsModelWrapper(
        modifier: Modifier = Modifier,
        viewModelStoreOwner: ViewModelStoreOwner,
        idArg: Int? = null,
        navigateBack: () -> Unit = {},
        showBackButton: Boolean = false,
        onRoute:(
            (destination: String,
             navOptions: NavOptions?,
             navigatorExtras: Navigator.Extras?)->Unit)?=null
    ){

        val validatedId = idArg?:0

        val viewModel: ProductDetailsViewModel = viewModel(
            viewModelStoreOwner = viewModelStoreOwner,
            factory = ProductDetailsModelFactory(validatedId)
        )

        LaunchedEffect(Unit) { viewModel.fetchProduct() }

        val product by viewModel.product.observeAsState()

        val loadState by viewModel.loadState.observeAsState()

        ProductDetails(
            modifier = modifier,
            product = product,
            loadState = loadState,
            onRefresh = viewModel::fetchProduct,
            navigateBack = navigateBack,
            showBackButton = showBackButton,
            onRoute = onRoute
        )
    }

    @Composable
    public fun ProductDetails(
        modifier: Modifier = Modifier,
        product: Product?,
        loadState: LoadState?,
        onRefresh: () -> Unit = {},
        navigateBack: () -> Unit = {},
        showBackButton: Boolean = false,
        onRoute:(
            (destination: String,
             navOptions: NavOptions?,
             navigatorExtras: Navigator.Extras?)->Unit)?=null
    ) {
        Scaffold(
            modifier = modifier,
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
            topBar = {
                if (showBackButton) {
                    ProductDetailsTopAppBar(
                        navigateBack = navigateBack,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                when(loadState) {
                    is LoadState.Error -> {
                        val loadStateError: LoadState.Error = loadState
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)){
                            ErrorMessage(
                                modifier = Modifier.align(Alignment.Center),
                                message = loadStateError.error.message,
                                onRertry = onRefresh
                            )
                        }

                    }
                    is LoadState.NotLoading -> {
                        product?.let {
                            ProductContent(
                                modifier = modifier.padding(12.dp),
                                product = it
                            )
                        }
                    }
                    else -> Unit
                }
            }
        }
    }


    @Composable
    private fun ProductContent(
        modifier: Modifier = Modifier,
        product: Product
    ) {

        val discountPrice:Int = remember(product.discountPercentage, product.price) {
            product.discountPrice
        }

        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            ImagesCarousel(
                images = product.images,
                modifier = Modifier.fillMaxWidth()
            )

            Text(text = "${product.brand} | ${product.title}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium)

            Text(text = "${stringResource(R.string.category)}: ${product.category}",
                fontSize = 14.sp)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                if (product.discountPercentage>0) {
                    Text(text = "${discountPrice}\$",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium)

                    Text(text = "${product.price}\$",
                        style = TextStyle(textDecoration = TextDecoration.LineThrough),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium)

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(7.dp))
                            .background(MaterialTheme.colorScheme.primary)
                    ) {
                        Text(text = "-${product.discountPercentage.toInt()}%",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(4.dp,0.dp))
                    }
                } else {
                    Text(text = "${product.price}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium)
                }
            }

            if (product.rating>0){
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(android.R.drawable.star_on),
                        tint = MaterialTheme.colorScheme.tertiary,
                        contentDescription = stringResource(R.string.rating)
                    )
                    Text(text = "${product.rating}",
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.tertiary,
                        fontSize = 18.sp
                    )
                }
            }

            Text(text = if (product.stock>0)
                stringResource(R.string.inStock, product.stock)
            else stringResource(R.string.outOfStock),
                fontSize = 14.sp)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "${stringResource(R.string.description)}:",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium)
                Text(text = "${product.description}")
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun ProductDetailsTopAppBar(
        navigateBack: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        TopAppBar(
            title = {
                Text(text = stringResource(R.string.details))
            },
            navigationIcon = {
                IconButton(onClick = navigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            },
            modifier = modifier
        )
    }

}