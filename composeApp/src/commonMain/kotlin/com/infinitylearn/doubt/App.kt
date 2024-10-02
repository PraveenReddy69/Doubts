package com.infinitylearn.doubt


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.infinitylearn.doubt.mainscreenui.CameraTextUI
import com.infinitylearn.doubt.mainscreenui.CameraTextUIActions
import com.infinitylearn.doubt.mainscreenui.MyQuestionUI
import com.infinitylearn.doubt.mainscreenui.NoQuestionsFound
import com.infinitylearn.doubt.mainscreenui.QuestionItem
import com.infinitylearn.doubt.mainscreenui.model.UIConfig
import com.infinitylearn.doubt.mainscreenui.model.observeCameraTextUI

import com.infinitylearn.doubt.model.GetDoubtsRequest
import com.infinitylearn.doubt.model.MyQuestionResponse
import com.infinitylearn.doubt.networking.DoubtsAPIClient
import com.infinitylearn.doubt.networking.SubjectGradeMappingItem
import com.infinitylearn.doubt.textscreen.TextScreen
import com.infinitylearn.doubt.utils.PLATFORM_DESKTOP
import com.infinitylearn.doubt.utils.PLATFORM_WEB
import com.infinitylearn.doubt.utils.getPlatformName
import kotlinx.coroutines.launch

import org.jetbrains.compose.ui.tooling.preview.Preview
import util.onError
import util.onSuccess


var showNoQuestionsFound by mutableStateOf(false)

@Composable
@Preview
fun App(client: DoubtsAPIClient) {
    var showSubjectDialog by remember { mutableStateOf(false) }
    var mSubjectList by remember { mutableStateOf<List<SubjectGradeMappingItem>?>(emptyList()) }

    MaterialTheme {
        var mDoubtsData by remember {
            mutableStateOf<List<MyQuestionResponse.QuestionData?>>(emptyList())
        }
        var currentPage by remember { mutableStateOf(1) }
        var isLoading by remember { mutableStateOf(false) }
        var hasMoreData by remember { mutableStateOf(true) }
        var showNoQuestionsFound by remember { mutableStateOf(false) } // State for showing NoQuestionsFound
        val scope = rememberCoroutineScope()

        scope.launch {
            client.getSubjectsMapping(11, 1).onSuccess { response ->
                mSubjectList = response
            }.onError {
                mSubjectList = emptyList()
            }
        }
        Column(
            Modifier.fillMaxWidth().background(color = Color(0xfffcfcfc)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Load doubts based on the current page
            fun loadDoubts() {
                if (!isLoading && hasMoreData) {
                    isLoading = true
                    val getDoubtsRequest = GetDoubtsRequest(page = currentPage, userId = 3485384)
                    scope.launch {
                        client.getAllDoubts(getDoubtsRequest).onSuccess { response ->
                            if (response.data.isNotEmpty()) {
                                mDoubtsData += response.data
                                currentPage++ // Increment current page for next load
                                showNoQuestionsFound = false // Reset visibility on success
                            } else {
                                hasMoreData = false // No more data to load
                                showNoQuestionsFound = true // Show NoQuestionsFound if no data
                            }
                        }.onError {
                            println("---------------Errorr->$it")
                            showNoQuestionsFound = true // Show NoQuestionsFound on error
                        }
                        isLoading = false // Reset loading state after data fetching
                    }
                }
            }


            // Initial load using LaunchedEffect to ensure it runs once
            LaunchedEffect(Unit) {
                loadDoubts()
            }

            // Passing parameters to CollapsingToolbarLayout
            CollapsingToolbarLayout(
                mDoubtsData = mDoubtsData,
                currentPage = currentPage,
                isLoading = isLoading,
                hasMoreData = hasMoreData,
                onLoadMore = { loadDoubts() }, // Load more data
                showNoQuestionsFound = showNoQuestionsFound,
                onResetFilters = {
                    // Handle reset filters logic here
                    showNoQuestionsFound = false // Reset the visibility if needed
                    currentPage = 1 // Reset to the first page if necessary
                    mDoubtsData = emptyList() // Clear the current doubts data
                    loadDoubts() // Load doubts again
                },
                cameraTextUIActions = CameraTextUIActions(navigateToCamera = {
                    println("Camera Action Clicked")
                }, navigateToText = {

                    showSubjectDialog = true
                    println("Text Action Clicked--------$showSubjectDialog")
                })
            )
        }

    }




    if (showSubjectDialog) {
        TextScreen(mSubjectList,client)
    }
}


var mUiConfig: UIConfig? = null

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CollapsingToolbarLayout(
    mDoubtsData: List<MyQuestionResponse.QuestionData?>,
    currentPage: Int,
    isLoading: Boolean,
    hasMoreData: Boolean,
    onLoadMore: () -> Unit,
    showNoQuestionsFound: Boolean, // Parameter to show the NoQuestionsFound composable
    onResetFilters: () -> Unit,
    cameraTextUIActions: CameraTextUIActions// Action for NoQuestionsFound
) {
    // Create a gradient brush for background
    val gradientBrush = Brush.linearGradient(
        colors = listOf(Color(0xFFFFFBEE), Color(0xFFFFEDAA))
    )

    // Remember scroll state for LazyColumn
    val scrollState = rememberLazyListState()
    val mScope = rememberCoroutineScope()
    // LazyColumn to handle scrolling with a sticky header
    LazyColumn(
        state = scrollState, modifier = Modifier.fillMaxSize() // Properly constrain the height
    ) {
        // First composable with background
        item {
            Box(
                modifier = Modifier.fillMaxWidth()
                    .wrapContentHeight() // Use wrapContentHeight to avoid infinite height
                    .background(brush = gradientBrush)
            ) {
                val platform = getPlatformName()

                mScope.launch {
                    mUiConfig = observeCameraTextUI()
                }


                CameraTextUI(
                    platform, uiConfig = mUiConfig, cameraTextUIActions = cameraTextUIActions
                )
            }
        }

        // Sticky header to make MyQuestionUI stick on top during scroll
        stickyHeader {
            Box(
                modifier = Modifier.fillMaxWidth().background(Color.White)
            ) {
                MyQuestionUI() // Assuming this is your sticky header UI
            }
        }

        // Check if there are no questions and show the NoQuestionsFound UI
        if (showNoQuestionsFound) {
            item {
                NoQuestionsFound {
                    onResetFilters()
                    println("sdsfsfsfdsdfdf")
                }
            }
        } else {
            // Display doubt question data
            items(mDoubtsData.size) { index ->
                val data = mDoubtsData[index]
                data?.let {
                    QuestionItem(it, getPlatformName())
                }

                // Load more when user reaches the end of the list
                if (index == mDoubtsData.size - 1 && hasMoreData && !isLoading) {
                    onLoadMore()
                }
            }
        }

        // Show loading spinner only if there are doubts available and loading
        if (isLoading && !showNoQuestionsFound) {
            item {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}


@Composable
fun DoubtQuestionData(mDoubtsData: List<MyQuestionResponse.QuestionData?>) {
    // Check platform and adjust layout
    if (getPlatformName() == PLATFORM_WEB || getPlatformName() == PLATFORM_DESKTOP) {
        // Use a grid layout for desktop platforms
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // 2 items per row for desktop
            modifier = Modifier.fillMaxWidth().height(800.dp) // Ensure the grid fills the width
        ) {
            items(mDoubtsData.size) { index ->
                val data = mDoubtsData[index]
                data?.let {
                    QuestionItem(it, getPlatformName())
                }
            }
        }
    } else {
        // Use a linear layout for mobile platforms
        LazyColumn(
            modifier = Modifier.fillMaxWidth().height(900.dp) // Add padding around the column
        ) {
            items(mDoubtsData.size) { index ->
                val data = mDoubtsData[index]
                data?.let {
                    QuestionItem(it, getPlatformName())
                }
            }
        }
    }
}


/*fun selectPhotoFromSystemWeb(onFileSelected: (File?) -> Unit) {
    val input = document.createElement("input") as HTMLInputElement
    input.type = "file"
    input.accept = "image/*"  // Only allow image files
    input.onchange = {
        val file = input.files?.item(0)  // Get the selected file
        onFileSelected(file)  // Pass the file back
    }
    input.click()  // Trigger the file dialog
}*/






