// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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

fun main() {
    val driver = ChromeDriverManager.DRIVER_NAME.asResourceFile
    val driverManager = ChromeDriverManager(driver)

    val wordListFile = "words.txt".asResourceFile
    val wordList = wordListFile.readLines()
    wordListFile.delete()
    val wordsManager = WordListManager(wordList)

    application {
        val windowState = rememberWindowState(
            size = WindowSize(1000.dp, 600.dp),
            position = WindowPosition(Alignment.Center),
        )

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
                colors = LightColors
            ) {
                Scaffold(scaffoldState = scaffoldState) {
                    Column {
                        TopAppBar(
                            title = { Text("Bomb Party Assistant") },
                            elevation = 16.dp
                        )
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
}