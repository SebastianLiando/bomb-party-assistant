package components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp

/**
 * Renders the room ID indicator.
 *
 * @param id The room ID.
 * @param modifier The modifier.
 */
@Composable
fun RoomId(id: String, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Text("Room ID:")
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = id.toUpperCase(Locale.current),
            style = LocalTextStyle.current.copy(fontWeight = FontWeight.Bold)
        )
    }
}