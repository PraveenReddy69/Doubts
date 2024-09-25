package com.infinitylearn.doubt



import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.infinitylearn.doubt.mainscreenui.CameraTextUI
import com.infinitylearn.doubt.mainscreenui.MyQuestionUI
import com.infinitylearn.doubt.mainscreenui.QuestionItem
import com.infinitylearn.doubt.model.MyQuestionResponse
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    MaterialTheme {
        Column(Modifier.fillMaxWidth().background(color = Color(0xfffcfcfc)), horizontalAlignment = Alignment.CenterHorizontally) {
            CollapsingToolbarLayout()
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CollapsingToolbarLayout() {
    // Create a gradient brush for background
    val gradientBrush = Brush.linearGradient(
        colors = listOf(Color(0xFFFFFBEE), Color(0xFFFFEDAA))
    )


    // LazyColumn to handle scrolling with a sticky header
    LazyColumn(
        state = rememberLazyListState(), modifier = Modifier.fillMaxSize()
    ) {
        // First composable with background
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(brush = gradientBrush)
            ) {
                CameraTextUI()
            }
        }

        // Sticky header to make MyQuestionUI stick on top during scroll
        stickyHeader {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                MyQuestionUI()
            }
        }
        val mData = MyQuestionResponse.QuestionData(
            success = true,
            doubtId = 33944,
            userId = 3994030,
            isImageDoubt = true,
            doubtImage = "https://infinitylearn-dc-global.s3.ap-south-1.amazonaws.com/dc/ocr_images/2024/08/08/34e1f5d9-f145-4fa1-a36b-7014defe7f5b.jpg",
            doubtText = "Explain atom",
            subjectId = 2,
            subjectName = "Physics",
            createdTime = "2024-09-03 12:29:35",
            modifiedTime = "2024-09-17 08:04:52",
            version = 1,
            active = true,
            status = "RESOLVED",
            userGradeId = 11,
            isBookmarked = false,
            minDisplayRate = 20.0,
            maxDisplayRate = 30.0,
            freeDoubt = false,
            qbId = "ILQ-908079",
            doubtType = "AUTOSOLVED",
            doubtCardStatus = "Solved",
            timeLeft = 0
        )

        // Additional content to scroll through
        items(20) { index ->
            QuestionItem(mData)
        }
    }


}

