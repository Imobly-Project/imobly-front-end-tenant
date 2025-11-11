package com.imobly.imobly

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform