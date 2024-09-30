package com.infinitylearn.doubt.networking


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SubjectGradeMappingItem(
    @SerialName("id") val id: Int,
    @SerialName("gradeId") val gradeId: Int,
    @SerialName("subject") val subject: Subject,
    @SerialName("subjectId") val subjectId: Int,
    @SerialName("personaId") val personaId: String,
    @SerialName("tenantId") val tenantId: Int,
    @SerialName("aiEnabled") val aiEnabled: Boolean,
    @SerialName("drsEnabled") val drsEnabled: Boolean,
    @SerialName("tutorEnabled") val tutorEnabled: Boolean
) {
    @Serializable
    data class Subject(
        @SerialName("id") val id: Int,
        @SerialName("name") val name: String,
        @SerialName("image") val image: String,
        @SerialName("description") val description: String?,
        @SerialName("personaId") val personaId: String,
        @SerialName("aiEnabled") val aiEnabled: Boolean
    )
}

