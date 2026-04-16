package com.ronia.fr.module01.ex01

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform