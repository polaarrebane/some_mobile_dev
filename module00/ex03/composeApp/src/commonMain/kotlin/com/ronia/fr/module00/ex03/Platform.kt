package com.ronia.fr.module00.ex03

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform