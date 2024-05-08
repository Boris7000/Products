package com.miracle.products.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miracle.products.R

@Composable
fun ErrorMessage(
    modifier: Modifier = Modifier,
    message: String?,
    onRertry:(()->Unit)?=null){
    Row(modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Icon(
            modifier = Modifier.align(Alignment.Top),
            painter = painterResource(R.drawable.baseline_error_outline_24),
            tint = MaterialTheme.colorScheme.error,
            contentDescription = stringResource(R.string.error)
        )
        if (onRertry!=null){
            val annotatedString = buildAnnotatedString {
                withStyle(style = SpanStyle(
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 18.sp
                )
                ){
                    append("${stringResource(R.string.error)}: ${message}.")
                }

                pushStringAnnotation(tag = "retry", annotation = "")
                withStyle(style = SpanStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp)
                ){
                    append(stringResource(R.string.tryAgain))
                }
                pop()
            }
            ClickableText(text = annotatedString, onClick = { onRertry.invoke() })
        } else {
            Text(text = "${stringResource(R.string.error)}: ${message}.",
                color = MaterialTheme.colorScheme.error)
        }
    }
}

