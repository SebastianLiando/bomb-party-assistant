// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowSize
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import components.ConnectionChip
import components.Settings
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import model.AppState
import model.SettingsState

val stateChanger = flow {
    var i = 0

    while (true) {
        if (i % 3 == 0) {
            emit(AppState.Disconnected(""))
        } else if (i % 3 == 1) {
            emit(AppState.Connecting(""))
        } else {
            emit(AppState.Connected("", ""))
        }

        i++
        delay(2000)
    }
}

lateinit var stateRandomizer: Job

fun main() {
    application {
        val windowState = rememberWindowState(
            size = WindowSize(800.dp, 600.dp)
        )

        Window(
            state = windowState,
            onCloseRequest = ::exitApplication,
            title = "Bomb Party Assistant"
        ) {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val scope = rememberCoroutineScope()
                    var settings by remember { mutableStateOf(SettingsState()) }
                    var appState by remember { mutableStateOf<AppState>(AppState.Disconnected("")) }

                    if (!::stateRandomizer.isInitialized) {
                        stateRandomizer = scope.launch {
                            stateChanger.collect {
                                appState = it
                            }
                        }
                    }

                    Column {
                        Settings(
                            settings,
                            onChanged = {
                                settings = it
                            },
                            modifier = Modifier.padding(16.dp)
                        )
                        ConnectionChip(state = appState)
                    }
                }
            }
        }
    }
}