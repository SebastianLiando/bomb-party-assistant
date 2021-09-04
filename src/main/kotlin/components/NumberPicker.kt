package components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * Renders a number picker.
 *
 * @param current The current value.
 * @param max The maximum number.
 * @param min The minimum number. Defaults to zero.
 * @param onChanged Callback when the current value needs to be changed.
 * @param modifier The modifier.
 */
@Composable
fun NumberPicker(
    current: Int?,
    max: Int,
    min: Int = 0,
    onChanged: (Int?) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        IconButton(
            onClick = {
                onChanged((current?.minus(1) ?: min).coerceAtLeast(0))
            }
        ) {
            Icon(Icons.Rounded.Remove, "Reduce one", tint = MaterialTheme.colors.primary)
        }

        BasicTextField(
            value = current?.toString() ?: "",
            onValueChange = { value ->
                if (value.isBlank()) {
                    onChanged(null)
                } else {
                    value.toIntOrNull()?.let {
                        onChanged(it.coerceIn(min..max))
                    }
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.width(50.dp),
            decorationBox = { textField ->
                Box(
                    modifier = Modifier
                        .border(1.dp, Color.LightGray, shape = RoundedCornerShape(4.dp))
                        .padding(vertical = 4.dp)
                ) {
                    textField()
                }
            },
            textStyle = MaterialTheme.typography.caption.copy(
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onSurface
            )
        )

        IconButton(
            onClick = {
                onChanged((current?.plus(1) ?: (min + 1)).coerceAtMost(max))
            }
        ) {
            Icon(Icons.Rounded.Add, "Add one", tint = MaterialTheme.colors.primary)
        }
    }
}