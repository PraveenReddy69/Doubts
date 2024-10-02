package com.infinitylearn.doubt.mainscreenui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.infinitylearn.doubt.model.CreateDoubtRequest
import com.infinitylearn.doubt.networking.DoubtsAPIClient
import com.infinitylearn.doubt.networking.SubjectGradeMappingItem
import com.infinitylearn.doubt.utils.AppFontFamily
import com.infinitylearn.doubt.utils.hexToColor
import doubts.composeapp.generated.resources.Res
import doubts.composeapp.generated.resources.ic_doubts_highlight_botany
import doubts.composeapp.generated.resources.ic_doubts_highlight_chemistry
import doubts.composeapp.generated.resources.ic_doubts_highlight_english
import doubts.composeapp.generated.resources.ic_doubts_highlight_maths
import doubts.composeapp.generated.resources.ic_doubts_highlight_physics
import doubts.composeapp.generated.resources.ic_doubts_highlight_science
import doubts.composeapp.generated.resources.ic_doubts_highlight_zoology
import doubts.composeapp.generated.resources.ic_doubts_subject_normal_bota
import doubts.composeapp.generated.resources.ic_doubts_subject_normal_chem
import doubts.composeapp.generated.resources.ic_doubts_subject_normal_english
import doubts.composeapp.generated.resources.ic_doubts_subject_normal_logical
import doubts.composeapp.generated.resources.ic_doubts_subject_normal_maths
import doubts.composeapp.generated.resources.ic_doubts_subject_normal_phy
import doubts.composeapp.generated.resources.ic_doubts_subject_normal_science
import doubts.composeapp.generated.resources.ic_doubts_subject_normal_zoology
import doubts.composeapp.generated.resources.lc_doubts_highlight_logicalreasoning

import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import util.onError
import util.onSuccess


@Composable
fun WebDialogContent(
    onDismissRequest: () -> Unit,
    subjects: List<SubjectGradeMappingItem>?,
    mDoubtsAPIClient: DoubtsAPIClient,
    searchQuery: String
) {
    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(dismissOnClickOutside = true)
    ) {

        if (subjects != null) {
            Box(
                modifier = Modifier.background(
                    hexToColor("#FFFFFF"), shape = RoundedCornerShape(20.dp)
                )
            ) {
                SubjectDialogContent(
                    subjects = subjects,
                    mDoubtsAPIClient = mDoubtsAPIClient,
                    searchQuery
                )
            }

        }
        // SubjectDialogContent()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectBottomSheet(
    isBottomSheet: Boolean,
    onDismissRequest: () -> Unit,
    subjects: List<SubjectGradeMappingItem>?,
    mDoubtsAPIClient: DoubtsAPIClient,
    searchQuery: String
) {
    // ModalBottomSheetState to control the state of the sheet
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
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
        modifier = Modifier, onDismissRequest = {
            scope.launch {
                bottomSheetState.hide()
                onDismissRequest() // Notify the external state that the sheet is dismissed
            }
        }, sheetState = bottomSheetState
    ) {

        Column(
            modifier = Modifier.fillMaxWidth().padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (subjects != null) {
                SubjectDialogContent(subjects = subjects, mDoubtsAPIClient, searchQuery)
            }
        }
    }
}


@Preview
@Composable
fun SubjectDialogContent(
    subjects: List<SubjectGradeMappingItem>,
    mDoubtsAPIClient: DoubtsAPIClient,
    searchQuery: String
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 50.dp).wrapContentHeight()
    ) {

        var selectedSubject by remember { mutableStateOf(-1) }
        var mSelectedSubject by remember { mutableStateOf<SubjectGradeMappingItem.Subject?>(null) }

        Text(
            text = "Select a Subject",
            color = Color(0xFF4E4B66),
            fontSize = 16.sp,
            fontFamily = AppFontFamily(),
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 10.dp, start = 26.dp, end = 26.dp)
        )

        Text(
            text = "From which this question is",
            color = Color(0xFF263643),
            fontSize = 14.sp,
            fontFamily = AppFontFamily(),
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 10.dp, start = 26.dp, end = 26.dp)
        )
        // Replace with actual RecyclerView-like items in Jetpack Compose (LazyColumn)

        LazyVerticalGrid(
            columns = GridCells.Fixed(3), // 2 items per row for desktop
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 50.dp)// Ensure the grid fills the width
        ) {
            items(subjects.size) { index ->
                val data = subjects[index]

                Column(modifier = Modifier.align(Alignment.CenterHorizontally)) {

                    SubjectIcon(subjectId = data.subjectId,
                        isSelected = data.subjectId == selectedSubject,
                        onSelect = { selectedSubject = it })

                    if (data.subjectId == selectedSubject) {
                        mSelectedSubject = data.subject
                    }

                    Spacer(modifier = Modifier.padding(vertical = 10.dp))

                    Text(
                        text = data.subject.name,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = Color(0xFF14142B), // Replace with your color resource
                        fontSize = 12.sp,
                        maxLines = 3,
                        fontFamily = AppFontFamily(),
                        fontWeight = FontWeight.Medium   // Replace with your font family if needed
                    )
                }


            }
        }


        Spacer(modifier = Modifier.padding(vertical = 10.dp))

        val mScope = rememberCoroutineScope()
        Box(modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp).width(220.dp)
            .align(Alignment.CenterHorizontally).height(56.dp)
            .background(color = Color(0XFF3C8DCB), shape = RoundedCornerShape(50.dp)).clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }) {
                mScope.launch {
                    val createDoubtRequest = CreateDoubtRequest(
                        doubtText = searchQuery,
                        subjectId = mSelectedSubject?.id ?: 1,
                        tenantId = 1,
                        userGradeId = 11,
                        userId = 3485384
                    )
                    mDoubtsAPIClient.createDoubt(createDoubtRequest).onSuccess { response ->
                        println("---------------Success->${response.doubtId}")

                    }.onError {
                        println("---------------Errorr->$it")

                    }
                }

            }, contentAlignment = Alignment.Center // This centers the content inside the Box
        ) {
            Text(
                text = "Ask",
                color = Color(0xFFFFFFFF),
                fontSize = 16.sp,
                fontFamily = AppFontFamily(),
                fontWeight = FontWeight.SemiBold
            )
        }


    }
}

@Composable
fun SubjectIcon(
    subjectId: Int, isSelected: Boolean, onSelect: (Int) -> Unit
) {
    val imageResource = when (subjectId) {
        1 -> if (isSelected) Res.drawable.ic_doubts_highlight_maths else Res.drawable.ic_doubts_subject_normal_maths
        2 -> if (isSelected) Res.drawable.ic_doubts_highlight_physics else Res.drawable.ic_doubts_subject_normal_phy
        3 -> if (isSelected) Res.drawable.ic_doubts_highlight_chemistry else Res.drawable.ic_doubts_subject_normal_chem
        4 -> if (isSelected) Res.drawable.ic_doubts_highlight_botany else Res.drawable.ic_doubts_subject_normal_bota
        5 -> if (isSelected) Res.drawable.ic_doubts_highlight_zoology else Res.drawable.ic_doubts_subject_normal_zoology
        6 -> if (isSelected) Res.drawable.ic_doubts_highlight_science else Res.drawable.ic_doubts_subject_normal_science
        7 -> if (isSelected) Res.drawable.ic_doubts_highlight_english else Res.drawable.ic_doubts_subject_normal_english
        8 -> if (isSelected) Res.drawable.lc_doubts_highlight_logicalreasoning else Res.drawable.ic_doubts_subject_normal_logical
        else -> Res.drawable.ic_doubts_highlight_maths // Fallback image
    }


    Box(modifier = Modifier.padding(vertical = 10.dp)
        .fillMaxWidth() // This ensures the Box takes the full width
        .clickable(indication = null,
            interactionSource = remember { MutableInteractionSource() }) {
            onSelect(subjectId)
        },
        contentAlignment = Alignment.Center // Aligns the content to the center of the Box
    ) {
        Image(
            painter = painterResource(imageResource),
            contentDescription = null,
            modifier = Modifier.size(80.dp)
        )
    }

}


