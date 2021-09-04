package components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import model.AppState
import theme.PastelGreen
import theme.PastelRed
import theme.PastelYellow

/**
 * Renders a Material chip.
 *
 * @param color The background color of the chip.
 * @param modifier The modifier.
 * @param child The content of the chip.
 */
@Composable
fun Chip(
    color: Color = Color.LightGray,
    modifier: Modifier = Modifier,
    child: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier,
        elevation = 8.dp,
        shape = RoundedCornerShape(16.dp),
        color = color
    ) {
        child()
    }
}

/**
 * Renders a chip to display the connection status.
 *
 * @param state Current application state.
 */
@Composable
fun ConnectionChip(state: AppState) {
    val chipColor = when (state) {
        is AppState.Disconnected -> PastelRed
        is AppState.Connecting -> PastelYellow
        is AppState.Connected -> PastelGreen
    }
    val chipText = when (state) {
        is AppState.Disconnected -> "Disconnected"
        is AppState.Connecting -> "Connecting"
        is AppState.Connected -> "Connected"
    }

    val animatedColor by animateColorAsState(chipColor)
    val modifier = Modifier.padding(vertical = 4.dp, horizontal = 12.dp)

    Chip(color = animatedColor) {
        Text(
            text = chipText,
            color = Color.Black,
            modifier = modifier
        )
    }
}