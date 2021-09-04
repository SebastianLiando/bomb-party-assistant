// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import extension.asResourceFile
import manager.ChromeDriverManager
import manager.WordListManager
import pages.AppContent
import theme.DarkColors
import theme.LightColors

const val DRIVER_EXE_NAME = "chromedriver.exe"
const val WORD_LIST_FILE_NAME = "words.txt"

fun main() {
    // Load the driver
    val driver = DRIVER_EXE_NAME.asResourceFile
    val driverManager = ChromeDriverManager(driver)

    // Load the word list.
    val wordListFile = WORD_LIST_FILE_NAME.asResourceFile
    val wordList = wordListFile.readLines()
    wordListFile.delete()
    val wordsManager = WordListManager(wordList)

    application {
        // Initial window state.
        val windowState = rememberWindowState(
            size = WindowSize(1000.dp, 600.dp),
            position = WindowPosition(Alignment.Center),
        )

        // Clean up web driver when the application quits.
        DisposableEffect(driverManager) {
            onDispose {
                if (driverManager.isDriverLoaded) {
                    driverManager.close()
                }
                driver.delete()
                println("Cleaned selenium driver")
            }
        }

        val scaffoldState = rememberScaffoldState()

        Window(
            state = windowState,
            onCloseRequest = ::exitApplication,
            title = "Bomb Party Assistant"
        ) {
            MaterialTheme(
                colors = if (isSystemInDarkTheme()) DarkColors else LightColors
            ) {
                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        TopAppBar(
                            title = { Text("Bomb Party Assistant") },
                            elevation = 16.dp
                        )
                    }
                ) {
                    AppContent(
                        driverManager = driverManager,
                        wordsManager = wordsManager,
                        scaffoldState = scaffoldState
                    )
                }
            }
        }
    }
}