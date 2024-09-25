package com.infinitylearn.doubt.model

import kotlinx.serialization.SerialName


data class MyQuestionResponse(
    @SerialName("count")
    val count: Int,
    @SerialName("data")
    val `data`: List<QuestionData>,
    @SerialName("pagination")
    val pagination: Pagination,
    @SerialName("success")
    val success: Boolean
) {
    data class QuestionData(
        @SerialName("active")
        val active: Boolean? = false,
        @SerialName("chapterId")
        val chapterId: Int? = null,
        @SerialName("chapterName")
        val chapterName: String? = null,
        @SerialName("chatSessionId")
        val chatSessionId: String? = null,
        @SerialName("created_time")
        val createdTime: String? = null,
        @SerialName("dismissedDoubt")
        val dismissedDoubt: DismissedDoubt? = null,
        @SerialName("doubtCardStatus")
        val doubtCardStatus: String? = null,
        @SerialName("doubtId")
        val doubtId: Long? = null,
        @SerialName("doubtImage")
        val doubtImage: String? = null,
        @SerialName("doubtText")
        val doubtText: String? = null,
        @SerialName("doubtType")
        val doubtType: String? = null,
        @SerialName("freeDoubt")
        val freeDoubt: Boolean? = null,
        @SerialName("isBookmarked")
        val isBookmarked: Boolean? = null,
        @SerialName("isImageDoubt")
        val isImageDoubt: Boolean? = null,
        @SerialName("maxDisplayRate")
        val maxDisplayRate: Double? = null,
        @SerialName("minDisplayRate")
        val minDisplayRate: Double? = null,
        @SerialName("modified_time")
        val modifiedTime: String? = null,
        @SerialName("ocrResponse")
        val ocrResponse: OcrResponse? = null,
        @SerialName("qbId")
        val qbId: String? = null,
        @SerialName("status")
        val status: String? = null,
        @SerialName("subjectId")
        val subjectId: Int? = null,
        @SerialName("subjectName")
        val subjectName: String? = null,
        @SerialName("success")
        val success: Boolean? = null,
        @SerialName("time_left")
        val timeLeft: Int? = null,
        @SerialName("userGradeId")
        val userGradeId: Int? = null,
        @SerialName("userId")
        val userId: Long? = null,
        @SerialName("version")
        val version: Int? = null,
        @SerialName("hlConversationId") val hlConversationId: String? = "",
        @SerialName("askFeedback") var askFeedback: Boolean? = null,
        @SerialName("assignedTo") val assignedTo: Long? = null,
    ) {
        data class DismissedDoubt(
            @SerialName("isdoubtEditable")
            val isdoubtEditable: Boolean,
            @SerialName("reason")
            val reason: String
        )

        data class OcrResponse(
            @SerialName("auto_rotate_confidence")
            val autoRotateConfidence: Double,
            @SerialName("auto_rotate_degrees")
            val autoRotateDegrees: Int,
            @SerialName("data")
            val `data`: List<Data>,
            @SerialName("html")
            val html: String,
            @SerialName("imageId")
            val imageId: Int,
            @SerialName("imagePath")
            val imagePath: String,
            @SerialName("isContainImage")
            val isContainImage: Boolean,
            @SerialName("is_handwritten")
            val isHandwritten: Boolean,
            @SerialName("is_printed")
            val isPrinted: Boolean,
            @SerialName("line_data")
            val lineData: List<Any>,
            @SerialName("request_id")
            val requestId: String,
            @SerialName("text")
            val text: String
        ) {
            data class Data(
                @SerialName("type")
                val type: String,
                @SerialName("value")
                val value: String
            )
        }
    }

    data class Pagination(
        @SerialName("currentPage")
        val currentPage: Int,
        @SerialName("limit")
        val limit: Int
    )
}