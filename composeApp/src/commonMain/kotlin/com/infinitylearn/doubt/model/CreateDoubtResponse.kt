package com.infinitylearn.doubt.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateDoubtResponse(
    @SerialName("doubtId")
    val doubtId: Int = 0,
    @SerialName("success")
    val success: Boolean = false
)