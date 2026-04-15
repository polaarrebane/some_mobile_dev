package com.ronia.fr.module00.module00_ex02

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform