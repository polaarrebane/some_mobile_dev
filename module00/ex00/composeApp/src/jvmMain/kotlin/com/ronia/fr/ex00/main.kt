package com.ronia.fr.ex00

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Module00_ex00",
    ) {
        App()
    }
}