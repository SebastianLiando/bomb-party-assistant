package theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

/**
 * Applies this application's theme to the content.
 *
 * @param content The application content.
 */
@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = if (isSystemInDarkTheme()) DarkColors else LightColors
    ) {
        content()
    }
}