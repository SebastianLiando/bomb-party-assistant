package components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlin.math.max
import kotlin.math.min

@Composable
fun NumberPicker(
    current: Int?,
    max: Int,
    min: Int = 0,
    onChanged: (Int?) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(
            onClick = {
                onChanged((current?.minus(1) ?: min).coerceAtLeast(0))
            }
        ) {
            Icon(Icons.Rounded.Remove, "Reduce one")
        }

        OutlinedTextField(
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
            isError = current == null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.size(100.dp, 50.dp),
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
        )

        IconButton(
            onClick = {
                onChanged((current?.plus(1) ?: (min + 1)).coerceAtMost(max))
            }
        ) {
            Icon(Icons.Rounded.Add, "Add one")
        }
    }
}