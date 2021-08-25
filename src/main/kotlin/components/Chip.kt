package components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Chip(
    color: Color = Color.LightGray,
    modifier: Modifier = Modifier,
    child: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier.padding(8.dp),
        elevation = 8.dp,
        shape = RoundedCornerShape(16.dp),
        color = color
    ) {
        child()
    }

}