package theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

// Colors for the connection status chip.
val PastelRed = Color(0xFFFF6961)
val PastelYellow = Color(0xFFFDFD56)
val PastelGreen = Color(0xFFB0FFAD)

// Light theme colors.
private val MediumPurple = Color(0xFF947BD3)
val LightColors = lightColors(
    primary = MediumPurple,
    primaryVariant = MediumPurple,
)

// Dark theme colors.
private val BlueBell = Color(0xFFB5A3E0)
val DarkColors = darkColors(
    primary = BlueBell,
    primaryVariant = BlueBell
)