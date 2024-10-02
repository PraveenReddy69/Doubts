package com.infinitylearn.doubt.utils

import androidx.compose.ui.graphics.Color
import doubts.composeapp.generated.resources.Res


fun hexToColor(hex: String): Color {

    val cleanedHex = hex.trim().removePrefix("#") // Remove '#' if present
    val colorInt = when (cleanedHex.length) {
        6 -> 0xFF000000.toInt() or cleanedHex.toLong(16).toInt() // Default alpha to 255
        8 -> cleanedHex.toLong(16).toInt() // Include alpha if provided
        else -> throw IllegalArgumentException("Invalid hex color: $hex")
    }
    return Color(colorInt)
}


/*

fun loadNormalSubjectImage(view: ImageView, subjectId: Int) {
    when (subjectId) {
        1 -> view.setImageResource(Res.drawable.ic_doubts_subject_normal_maths)
        2 -> view.setImageResource(R.drawable.ic_doubts_subject_normal_phy)
        3 -> view.setImageResource(R.drawable.ic_doubts_subject_normal_chem)
        4 -> view.setImageResource(R.drawable.ic_doubts_subject_normal_bota)
        5 -> view.setImageResource(R.drawable.ic_doubts_subject_normal_zoology)
        6 -> view.setImageResource(R.drawable.ic_doubts_subject_normal_science)
        7 -> view.setImageResource(R.drawable.ic_doubts_subject_normal_english)
        8 -> view.setImageResource(R.drawable.ic_doubts_subject_normal_logical)
    }
}

fun loadHightligtedSubjectImage(view: ImageView, subjectId: Int) {
    when (subjectId) {
        1 -> view.setImageResource(R.drawable.ic_doubts_highlight_maths)
        2 -> view.setImageResource(R.drawable.ic_doubts_highlight_physics)
        3 -> view.setImageResource(R.drawable.ic_doubts_highlight_chemistry)
        4 -> view.setImageResource(R.drawable.ic_doubts_highlight_botany)
        5 -> view.setImageResource(R.drawable.ic_doubts_highlight_zoology)
        6 -> view.setImageResource(R.drawable.ic_doubts_highlight_science)
        7 -> view.setImageResource(R.drawable.ic_doubts_highlight_english)
        8 -> view.setImageResource(R.drawable.lc_doubts_highlight_logicalreasoning)
    }
}
*/

