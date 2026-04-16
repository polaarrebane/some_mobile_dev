package com.ronia.fr.module01.ex00

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform