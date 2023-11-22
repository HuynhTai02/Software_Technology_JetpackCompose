package dev.amal.booksapp.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import dev.amal.booksapp.R
import dev.amal.booksapp.components.TopBar
import dev.amal.booksapp.navigation.MainActions
import dev.amal.booksapp.ui.theme.typography
import dev.amal.booksapp.utils.DetailViewState
import dev.amal.booksapp.viewmodel.MainViewModel
import dev.amal.booksapp.components.BookDetailsCard

@ExperimentalCoilApi
@Composable
fun BookDetailsScreen(viewModel: MainViewModel, actions: MainActions) {

    Scaffold(topBar = {
        TopBar(title = stringResource(id = R.string.text_bookDetails),
            action = actions)
    }) {
        BookDetails(viewModel = viewModel)
    }

}

@SuppressLint("StateFlowValueCalledInComposition")
@ExperimentalCoilApi
@Composable
fun BookDetails(viewModel: MainViewModel) {
    when (val result = viewModel.bookDetails.value) {
        DetailViewState.Loading -> Text(text = "Loading")
        is DetailViewState.Error -> Text(text = "Error found: ${result.exception}")
        is DetailViewState.Success -> {
            val book = result.data

            LazyColumn {
                // Book Details Card
                item {
                    BookDetailsCard(book.title, book.authors, book.thumbnailUrl, book.categories)
                }

                // Description
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = stringResource(id = R.string.text_content),
                        style = typography.h6,
                        color = MaterialTheme.colors.primaryVariant,
                        modifier = Modifier.padding(start = 18.dp, end = 18.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = book.longDescription.replace("\\s+".toRegex(), " ").trim(),
                        style = typography.body1,
                        textAlign = TextAlign.Justify,
                        color = MaterialTheme.colors.primaryVariant.copy(0.7F),
                        modifier = Modifier.padding(start = 18.dp, end = 18.dp)
                    )
                }
            }

        }

        DetailViewState.Empty -> Text("No results found!")
    }
}