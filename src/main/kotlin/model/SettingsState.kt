package model

/**
 * Contains state for the app's settings.
 *
 * @property maxWordLength The max word length for the answer suggestions.
 * @property alwaysCopyFirstItemToClip If `true`, always copy the first answer suggestion to the clipboard.
 */
data class SettingsState(
    val maxWordLength: Int? = 10,
    val alwaysCopyFirstItemToClip: Boolean = false
)
