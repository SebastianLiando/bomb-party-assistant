package manager

class WordListManager(private val words: List<String>) {
    fun queryWords(substring: String, maxLength: Int = Int.MAX_VALUE): List<String> {
        return words
            .filter { word -> word.length <= maxLength }
            .filter { word -> word.contains(substring, ignoreCase = true) }
    }
}