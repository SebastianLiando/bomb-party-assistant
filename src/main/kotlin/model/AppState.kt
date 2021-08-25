package model

sealed class AppState {
    data class Disconnected(val editingRoomId: String) : AppState()

    data class Connecting(val roomId: String) : AppState()

    data class Connected(val roomId: String, val prompt: String) : AppState()
}
