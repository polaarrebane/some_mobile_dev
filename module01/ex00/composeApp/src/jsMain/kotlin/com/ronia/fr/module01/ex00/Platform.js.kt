package com.ronia.fr.module01.ex00

class JsPlatform: Platform {
    override val name: String = "Web with Kotlin/JS"
}

actual fun getPlatform(): Platform = JsPlatform()