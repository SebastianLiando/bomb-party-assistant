package pages

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import components.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import manager.ChromeDriverManager
import manager.WordListManager
import model.AppState
import model.SettingsState
import org.openqa.selenium.SessionNotCreatedException
import org.openqa.selenium.TimeoutException
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection

/**
 * The app header when the app is in disconnected state.
 *
 * @param state Current app state.
 * @param onRoomIdChange Called when the room ID needs to change.
 * @param onConnect Called when the user wants to connect to a room.
 * @param modifier The modifier.
 */
@Composable
fun DisconnectedHeader(
    state: AppState.Disconnected, onRoomIdChange: (String) -> Unit, onConnect: () -> Unit, modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = modifier
    ) {
        val isTextFieldError = state.editingRoomId.isNotEmpty() && !state.roomIdValid

        Column(modifier = Modifier.weight(1f)) {
            OutlinedTextField(
                value = state.editingRoomId, onValueChange = onRoomIdChange, label = {
                    Text("Room ID")
                }, isError = isTextFieldError, modifier = Modifier.fillMaxWidth()
            )

            if (isTextFieldError) {
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "ID must consist of 4 alphabets!",
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption
                )
            }
        }

        Spacer(Modifier.width(16.dp))
        Button(onClick = onConnect, enabled = state.roomIdValid) {
            Text("CONNECT")
        }
    }
}

/**
 * The app header when the app is in connecting state.
 *
 * @param roomId The room ID the app is connecting to.
 * @param modifier The modifier.
 */
@Composable
fun ConnectingHeader(roomId: String, modifier: Modifier = Modifier) {
    RoomId(roomId, modifier)
}

/**
 * The app header when the app is in connected state.
 *
 * @param roomId The room ID it is connected to.
 * @param onDisconnect Called when the user wants to disconnect.
 * @param modifier The modifier.
 */
@Composable
fun ConnectedHeader(
    roomId: String, onDisconnect: () -> Unit, modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = modifier
    ) {
        RoomId(roomId, Modifier.weight(1f))
        TextButton(onClick = onDisconnect) {
            Text("DISCONNECT")
        }
    }
}

/**
 * Renders the header of the application.
 *
 * @param state Current app state.
 * @param onChange Called when the app state needs to change.
 * @param onConnect Called when the user wants to connect to a room.
 * @param modifier The modifier.
 */
@Composable
fun AppHeader(
    state: AppState, onChange: (AppState) -> Unit, onConnect: (Boolean) -> Unit, modifier: Modifier = Modifier
) {
    when (state) {
        is AppState.Disconnected -> DisconnectedHeader(state, onRoomIdChange = {
            onChange(state.copy(editingRoomId = it.take(4)))
        }, onConnect = { onConnect(true) }, modifier = modifier
        )
        is AppState.Connecting -> ConnectingHeader(state.roomId, modifier)
        is AppState.Connected -> ConnectedHeader(
            roomId = state.roomId, onDisconnect = { onConnect(false) }, modifier = modifier
        )
    }
}

/**
 * The answer section when the app is in disconnected state.
 *
 * @param modifier The modifier.
 */
@Composable
fun DisconnectedAnswerSection(modifier: Modifier = Modifier) = Column(
    horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier, verticalArrangement = Arrangement.Center
) {
    Text(
        text = "Connect to a Room to Get Started", style = MaterialTheme.typography.h6, textAlign = TextAlign.Center
    )
    Text(
        text = "Enter your game’s 4 letter room ID and click the connect button.", textAlign = TextAlign.Center
    )
}

/**
 * The answer section when the app is in connecting state.
 *
 * @param modifier The modifier.
 */
@Composable
fun ConnectingAnswerSection(modifier: Modifier = Modifier) = Column(
    horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = modifier
) {
    CircularProgressIndicator()
    Spacer(Modifier.height(8.dp))
    Text("Connecting to the room...")
}

/**
 * Renders the answer section.
 *
 * @param appState Current app state.
 * @param settingsState Current settings state.
 * @param wordsManager Words manager.
 * @param modifier The modifier.
 */
@Composable
fun AnswersSection(
    appState: AppState, settingsState: SettingsState, wordsManager: WordListManager, modifier: Modifier = Modifier
) {
    when (appState) {
        is AppState.Disconnected -> DisconnectedAnswerSection(modifier)
        is AppState.Connecting -> ConnectingAnswerSection(modifier)
        is AppState.Connected -> {
            val answers =
                wordsManager.queryWords(appState.prompt, settingsState.maxWordLength ?: Int.MAX_VALUE).shuffled()

            // Copy to clipboard
            if (settingsState.alwaysCopyFirstItemToClip) {
                val clipboard = Toolkit.getDefaultToolkit().systemClipboard
                answers.firstOrNull()?.let { toCopy ->
                    val selection = StringSelection(toCopy)
                    clipboard.setContents(selection, selection)
                }
            }

            Answers(
                answers = answers,
                modifier = modifier,
                minCellSize = if ((settingsState.maxWordLength ?: Int.MAX_VALUE) > 10) {
                    200.dp
                } else {
                    150.dp
                }
            )
        }
    }
}

/**
 * Renders the application.
 *
 * @param driverManager Web driver manager.
 * @param wordsManager Words manager.
 * @param scaffoldState Scaffold state.
 */
@Composable
fun AppContent(
    driverManager: ChromeDriverManager, wordsManager: WordListManager, scaffoldState: ScaffoldState
) {
    val scope = rememberCoroutineScope()

    var appState by remember { mutableStateOf<AppState>(AppState.Disconnected("")) }
    var settingsState by remember { mutableStateOf(SettingsState()) }

    println(appState)

    val currentAppState = appState
    val shouldStartConnection = currentAppState !is AppState.Disconnected
    DisposableEffect(shouldStartConnection) {
        if (shouldStartConnection) {
            val roomId = when (currentAppState) {
                is AppState.Connected -> currentAppState.roomId
                is AppState.Connecting -> currentAppState.roomId
                else -> throw Exception("Unexpected error!")
            }

            val syllablesFlow = driverManager.inspectRoom(roomId)
            val job = scope.launch(Dispatchers.IO) {
                println("Collecting syllable flow")

                syllablesFlow.distinctUntilChanged().catch { error ->
                    println(error)

                    val message = when (error) {
                        is TimeoutException -> "Request timeout! Ensure that the room id is correct and you have stable connection."
                        is SessionNotCreatedException -> "Failed to create session! Try replacing the chrome driver that matches your OS and Google Chrome version."
                        else -> error.localizedMessage
                    }

                    scaffoldState.snackbarHostState.showSnackbar(
                        message = message,
                        actionLabel = "OK",
                        duration = SnackbarDuration.Long,
                    )

                    appState = AppState.Disconnected(roomId)

                }.collect {
                    appState = AppState.Connected(roomId, it)
                }
            }

            onDispose {
                job.cancel()
                println("Stopped flow collection")
            }
        } else {
            onDispose { }
        }
    }

    Column(modifier = Modifier.padding(16.dp).fillMaxHeight()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AppHeader(state = appState,
                modifier = Modifier.weight(1f),
                onChange = { appState = it },
                onConnect = { toConnect ->
                    if (toConnect) {
                        (appState as? AppState.Disconnected)?.let {
                            appState = AppState.Connecting(it.editingRoomId)
                        }
                    } else {
                        appState = AppState.Disconnected()
                    }
                })
            Spacer(Modifier.width(16.dp))
            ConnectionChip(appState)
        }

        Spacer(Modifier.height(16.dp))

        Divider()

        Row(verticalAlignment = Alignment.CenterVertically) {
            PromptBox(appState.prompt, modifier = Modifier.padding(16.dp))
            Divider(modifier = Modifier.padding(vertical = 4.dp).height(80.dp).width(1.dp))
            Settings(
                settings = settingsState, onChanged = { settingsState = it }, modifier = Modifier.padding(start = 16.dp)
            )
        }

        Divider()

        Spacer(Modifier.height(16.dp))

        AnswersSection(
            appState = appState,
            settingsState = settingsState,
            wordsManager = wordsManager,
            modifier = Modifier.weight(1f).fillMaxWidth()
        )
    }
}