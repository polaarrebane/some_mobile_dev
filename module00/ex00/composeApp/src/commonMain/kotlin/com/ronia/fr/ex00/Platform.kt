package com.ronia.fr.ex00

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform