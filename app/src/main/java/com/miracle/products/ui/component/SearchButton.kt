package com.miracle.products.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchButton(modifier: Modifier = Modifier,
                 onClick: () -> Unit = {},
                 placeholder: @Composable() (() -> Unit)? = null,
                 leadingIcon: @Composable() (() -> Unit)? = null,
                 trailingIcon: @Composable() (() -> Unit)? = null,
                 backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
                 shape: Shape = CircleShape) {
    Surface(
        modifier = modifier,
        color = backgroundColor,
        shape = shape,
        onClick = onClick
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(SearchBarDefaults.InputFieldHeight),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.offset(x = 4.dp).then(Modifier.defaultMinSize(48.dp, 48.dp)),
                contentAlignment = Alignment.Center){
                leadingIcon?.invoke()
            }
            Box(modifier = Modifier.weight(1f)){
                placeholder?.invoke()
            }
            Box(Modifier.offset(x = -4.dp).then(Modifier.defaultMinSize(48.dp, 48.dp)),
                contentAlignment = Alignment.Center){
                trailingIcon?.invoke()
            }
        }
    }
}

@Preview
@Composable
fun SearchButtonPreview() {
    Surface(modifier = Modifier.size(300.dp, 64.dp)) {
        SearchButton(
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            placeholder = {
                Text("Search products")
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = null
                )
            }
        )
    }
}



