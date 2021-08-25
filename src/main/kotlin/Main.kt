// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowSize
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import components.NumberPicker

fun main() {
    application {
        val windowState = rememberWindowState(
            size = WindowSize(800.dp, 600.dp)
        )

        Window(state = windowState, onCloseRequest = ::exitApplication, title = "Bomb Party Assistant") {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    var currentNumber by remember { mutableStateOf<Int?>(0) }
                    NumberPicker(currentNumber, 10, onChanged = { newValue -> currentNumber = newValue })
                }
            }
        }
    }
}