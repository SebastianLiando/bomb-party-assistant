package components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Renders the grid of words that the player can use as an answer.
 *
 * @param answers All the answers the player can use.
 * @param minCellSize The minimum cell size.
 * @param modifier The modifier.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Answers(answers: List<String>, minCellSize: Dp, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        cells = GridCells.Adaptive(minCellSize),
        modifier = modifier,
    ) {
        items(answers) { answer ->
            Card(
                modifier = Modifier.padding(4.dp)
            ) {
                Text(
                    answer.toUpperCase(Locale.current),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                )
            }

        }
    }

}