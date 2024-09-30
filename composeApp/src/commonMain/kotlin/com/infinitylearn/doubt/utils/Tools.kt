package com.infinitylearn.doubt.utils

import androidx.compose.ui.graphics.Color




fun hexToColor(hex: String): Color {
    val cleanedHex = hex.trim().removePrefix("#") // Remove '#' if present
    val colorInt = when (cleanedHex.length) {
        6 -> 0xFF000000.toInt() or cleanedHex.toLong(16).toInt() // Default alpha to 255
        8 -> cleanedHex.toLong(16).toInt() // Include alpha if provided
        else -> throw IllegalArgumentException("Invalid hex color: $hex")
    }
    return Color(colorInt)
}

