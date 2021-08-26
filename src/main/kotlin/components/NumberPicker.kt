package components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.max
import kotlin.math.min

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
//            isError = current == null,
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
            textStyle = MaterialTheme.typography.caption.copy(textAlign = TextAlign.Center)
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