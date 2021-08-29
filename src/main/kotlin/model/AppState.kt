package model

sealed class AppState(open val prompt: String?) {
    data class Disconnected(
        val editingRoomId: String = "",
        val requestConnect: Boolean = false
    ) : AppState(null) {
        val roomIdValid: Boolean
            get() = editingRoomId.length == 4 && editingRoomId.all { it.isLetter() }
    }

    data class Connecting(val roomId: String) : AppState(null)

    data class Connected(val roomId: String, override val prompt: String) : AppState(prompt)
}
