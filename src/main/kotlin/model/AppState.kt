package model

/**
 * This class contains the application state.
 *
 * @property prompt Current syllable prompt.
 */
sealed class AppState(open val prompt: String?) {
    /**
     * This class contains state when the application is not connected to any room.
     *
     * @property editingRoomId The room ID in the text field.
     */
    data class Disconnected(val editingRoomId: String = "") : AppState(null) {

        /** Returns `true` if the room ID is a valid room ID. For a room ID to be valid,
         * it must be of length 4 and consisting only of English alphabets.
         *
         * Note that this does not check if the room ID exists. */
        val roomIdValid: Boolean
            get() = editingRoomId.length == 4 && editingRoomId.all { it.isLetter() }
    }

    /**
     * This class contains state when the application is connecting to a room.
     *
     * @property roomId The room ID it is connecting to.
     */
    data class Connecting(val roomId: String) : AppState(null)

    /**
     * This class contains state when the application is connected to a room.
     *
     * @property roomId The room ID it is connected to.
     * @property prompt The current syllable prompt that the room is asking.
     */
    data class Connected(val roomId: String, override val prompt: String) : AppState(prompt)
}
