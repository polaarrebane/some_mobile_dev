package com.ronia.fr.module00.ex01

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Module00_ex01",
    ) {
        App()
    }
}