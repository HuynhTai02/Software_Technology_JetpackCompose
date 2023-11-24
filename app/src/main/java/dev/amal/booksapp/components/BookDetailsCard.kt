package dev.amal.booksapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.flowlayout.FlowRow
import dev.amal.booksapp.ui.theme.typography

@ExperimentalCoilApi
@Composable
fun BookDetailsCard(
    title: String,
    authors: List<String>,
    thumbnailUrl: String,
    categories: List<String>
) {
    Box(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 20.dp, end = 16.dp, top = 40.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(color = Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(100.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colors.onSurface),
        )
        BookImageContentView(title, authors, thumbnailUrl, categories)
    }
}

@ExperimentalCoilApi
@Composable
fun BookImageContentView(
    title: String,
    authors: List<String>,
    thumbnailUrl: String,
    categories: List<String>
) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = rememberImagePainter(
                data = thumbnailUrl
            ),
            contentDescription = title,
            modifier = Modifier.size(240.dp, 140.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.onSurface)
                .padding(bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = typography.h6,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primaryVariant
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = authors.toString().removePrefix("[").removeSuffix("]"),
                style = typography.caption,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primaryVariant.copy(0.7F)
            )
            Spacer(modifier = Modifier.height(12.dp))
            FlowRow {
                categories.forEach {
                    ChipView(category = listOf(it))
                }
            }
        }
    }
}
