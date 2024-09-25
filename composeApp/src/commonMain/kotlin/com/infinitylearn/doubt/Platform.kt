package com.infinitylearn.doubt

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform