package com.ronia.fr.module00.ex03

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Module00_ex03",
    ) {
        App()
    }
}