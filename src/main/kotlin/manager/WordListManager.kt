package manager

/**
 * Manages the answer words.
 *
 * @property words List of words in the dictionary.
 */
class WordListManager(private val words: List<String>) {
    /**
     * Returns words that satisfy the given criteria.
     *
     * @param substring The substring that must be present in a word.
     * @param maxLength The maximum length of the word. By default, it is unbounded.
     * @return Words with the given criteria.
     */
    fun queryWords(substring: String, maxLength: Int = Int.MAX_VALUE): List<String> {
        return words
            .filter { word -> word.length <= maxLength }
            .filter { word -> word.contains(substring, ignoreCase = true) }
    }
}