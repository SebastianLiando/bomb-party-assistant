package components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.SettingsState

@Composable
private fun MaxWordLengthSettings(current: Int?, onChanged: (Int?) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            "Maximum suggestion word length",
            modifier = Modifier.weight(1f)
        )
        NumberPicker(
            current = current,
            max = 15,
            onChanged = onChanged,
        )
    }
}

@Composable
private fun CopyFirstToClipboardSettings(copy: Boolean, onChanged: (Boolean) -> Unit) {
    Row {
        Text(
            "Always copy first item to clipboard",
            modifier = Modifier.weight(1f)
        )
        Checkbox(copy, onCheckedChange = onChanged)
    }
}

@Composable
fun Settings(
    settings: SettingsState,
    onChanged: (SettingsState) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        MaxWordLengthSettings(
            settings.maxWordLength,
            onChanged = {
                onChanged(settings.copy(maxWordLength = it))
            }
        )

        Spacer(Modifier.height(8.dp))

        CopyFirstToClipboardSettings(
            settings.alwaysCopyFirstItemToClip,
            onChanged = {
                onChanged(settings.copy(alwaysCopyFirstItemToClip = it))
            }
        )
    }
}