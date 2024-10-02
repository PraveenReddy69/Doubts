package com.infinitylearn.doubt.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateDoubtRequest(
    @SerialName("doubtImage")
    val doubtImage: String = "",
    @SerialName("doubtText")
    val doubtText: String = "",
    @SerialName("doubtType")
    val doubtType: String = "OPEN",
    @SerialName("freeDoubt")
    val freeDoubt: Boolean = false,
    @SerialName("subjectId")
    val subjectId: Int = 0,
    @SerialName("tenantId")
    val tenantId: Int = 0,
    @SerialName("userGradeId")
    val userGradeId: Int = 0,
    @SerialName("userId")
    val userId: Int = 0
)