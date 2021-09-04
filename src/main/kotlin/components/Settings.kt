package components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.SettingsState

/**
 * Renders the UI for setting maximum word length.
 *
 * @param current The current maximum word length.
 * @param onChanged Callback when the maximum word length needs to change.
 */
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

/**
 * Renders the UI for setting copy first word to clipboard.
 *
 * @param copy Whether to automatically copy first word to clipboard
 * @param onChanged Callback when the settings need to change.
 */
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

/**
 * Renders the UI for settings.
 *
 * @param settings Current settings.
 * @param onChanged Callback when the settings needs to be changed.
 * @param modifier The modifier.
 */
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