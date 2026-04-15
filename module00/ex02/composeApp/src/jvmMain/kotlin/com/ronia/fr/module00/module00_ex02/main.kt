package com.ronia.fr.module00.module00_ex02

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Module00_ex02",
    ) {
        App()
    }
}