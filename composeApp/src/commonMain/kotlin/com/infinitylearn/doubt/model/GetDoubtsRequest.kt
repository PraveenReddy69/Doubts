package com.infinitylearn.doubt.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetDoubtsRequest(
    @SerialName("page")
    val page: Int = 1,
    @SerialName("status")
    val status: List<String> = listOf(),
    @SerialName("subjectIds")
    val subjectIds: List<String> = listOf(),
    @SerialName("userId")
    val userId: Int = 0
)