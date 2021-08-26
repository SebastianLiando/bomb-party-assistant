package components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Answers(answers: List<String>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        cells = GridCells.Adaptive(100.dp),
        modifier = modifier,
    ) {
        items(answers) { answer ->
            Text(
                answer.toUpperCase(Locale.current),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
        }
    }

}