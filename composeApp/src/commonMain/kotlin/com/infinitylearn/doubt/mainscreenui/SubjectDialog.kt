package com.infinitylearn.doubt.mainscreenui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil3.Image
import com.infinitylearn.doubt.networking.SubjectGradeMappingItem
import com.infinitylearn.doubt.utils.AppFontFamily
import com.infinitylearn.doubt.utils.getPlatformName
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import doubts.composeapp.generated.resources.Res
import doubts.composeapp.generated.resources.ic_maths_new
import kotlinx.coroutines.launch

@Composable
fun SubjectDialog(isBottomSheet: Boolean? = false) {
    println("----------------------->"+isBottomSheet)
    if (isBottomSheet == true) {
        ParentComposable(isBottomSheet)
    } else {
        WebDialogContent()
    }
}

@Composable
fun WebDialogContent() {
    Dialog(
        onDismissRequest = { /* Handle dismiss */ },
        properties = DialogProperties(dismissOnClickOutside = true)
    ) {
        // SubjectDialogContent()
    }
}


@Composable
fun ParentComposable(isBottomSheet: Boolean) {

    var isBottomSheetVisible by remember { mutableStateOf(true) }


    // Show the bottom sheet when the state is true
   /* if (isBottomSheetVisible) {
        SubjectBottomSheet(isBottomSheet = isBottomSheetVisible,
            onDismiss = { isBottomSheetVisible = false } // Update state when dismissed
        )
    }*/
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectBottomSheet(
    isBottomSheet: Boolean, onDismissRequest: () -> Unit,
    subjects: List<SubjectGradeMappingItem>?
) {
    // ModalBottomSheetState to control the state of the sheet
    val bottomSheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    // Launch effect to manage the state of the bottom sheet based on isBottomSheet
    LaunchedEffect(isBottomSheet) {
        if (isBottomSheet) {
            scope.launch {
                bottomSheetState.show()
            }
        } else {
            scope.launch {
                bottomSheetState.hide()
            }
        }
    }

    // ModalBottomSheet with updated state
    ModalBottomSheet(
        onDismissRequest = {
            scope.launch {
                bottomSheetState.hide()
                onDismissRequest() // Notify the external state that the sheet is dismissed
            }
        }, sheetState = bottomSheetState
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (subjects != null) {
                SubjectDialogContent(subjects = subjects)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {
                scope.launch {
                    bottomSheetState.hide()
                    onDismissRequest() // Notify the external state on manual dismissal
                }
            }) {
                Text("Close Sheet")
            }
        }
    }
}


@Composable
fun SubjectDialogContent(subjects: List<SubjectGradeMappingItem>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Select a Subject",
            color = Color(0xFF4E4B66),
            fontSize = 16.sp,
            fontFamily = AppFontFamily(),
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(vertical = 10.dp)
        )

        Text(
            text = "From which this question is",
            color = Color(0xFF263643),
            fontSize = 16.sp,
            fontFamily = AppFontFamily(),
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(vertical = 10.dp)
        )
        // Replace with actual RecyclerView-like items in Jetpack Compose (LazyColumn)

        LazyVerticalGrid(
            columns = GridCells.Fixed(3), // 2 items per row for desktop
            modifier = Modifier.fillMaxWidth().height(800.dp) // Ensure the grid fills the width
        ) {
            items(subjects.size) { index ->
                val data = subjects[index]

                Column {


                    Spacer(modifier = Modifier.padding(vertical = 10.dp))


                }

                Text(
                    text = data.subject.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f) // Weight to allow Text to take up space and center vertically
                        .padding(horizontal = 2.dp),
                    color = Color(0xFF14142B), // Replace with your color resource
                    fontSize = 12.sp,
                    maxLines = 3,
                    fontFamily = AppFontFamily(),
                    fontWeight = FontWeight.Medium   // Replace with your font family if needed
                )

            }
        }


        Spacer(modifier = Modifier.height(16.dp))


        Spacer(modifier = Modifier.height(16.dp))


    }
}


