package com.ronia.fr.module01.ex00

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Module01_ex00",
    ) {
        App()
    }
}