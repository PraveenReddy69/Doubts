package com.infinitylearn.doubt.textscreen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.infinitylearn.doubt.mainscreenui.SubjectBottomSheet
import com.infinitylearn.doubt.mainscreenui.WebDialogContent
import com.infinitylearn.doubt.networking.DoubtsAPIClient
import com.infinitylearn.doubt.networking.SubjectGradeMappingItem
import com.infinitylearn.doubt.utils.AppFontFamily
import com.infinitylearn.doubt.utils.PLATFORM_DESKTOP
import com.infinitylearn.doubt.utils.PLATFORM_WEB
import com.infinitylearn.doubt.utils.getPlatformName
import doubts.composeapp.generated.resources.Res
import doubts.composeapp.generated.resources.ic_back_new
import org.jetbrains.compose.resources.painterResource

import org.jetbrains.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TextScreen(mSubjectList: List<SubjectGradeMappingItem>?,mClient: DoubtsAPIClient) {


    var searchQuery by remember { mutableStateOf("") }
    var showSubjectDialog by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {


        Row(modifier = Modifier.padding(top = 20.dp, start = 20.dp)) {

            Image(
                painter = painterResource(Res.drawable.ic_back_new),
                contentDescription = "",
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterVertically)// Set appropriate size if needed
            )

            // Title Text
            Text(
                text = "Ask Question",
                color = Color(0xFF4E4B66),
                fontFamily = AppFontFamily(),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,

                modifier = Modifier
                    .padding(start = 10.dp)
                    .align(Alignment.CenterVertically)
            )
        }



        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text(text = "Start Typing") },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(color = Color.White),
            textStyle = TextStyle(
                color = Color(0xFF14142B),
                fontFamily = AppFontFamily(),
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            ),
            singleLine = false,
            maxLines = 5,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedTextColor = Color.Black,
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )


        // ProgressButton (Custom Button)
        Box(
            modifier = Modifier
                .padding(vertical = 20.dp, horizontal = 20.dp)
                .width(220.dp)
                .align(Alignment.CenterHorizontally)
                .height(56.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }) {
                    //handle Click
                    if (searchQuery?.length ?: 0 > 5) {
                        showSubjectDialog = true

                        println("Show subject Dialog-----$searchQuery")
                    } else {
                        println("Don't show subject Dialog")
                    }
                }
                .background(color = Color(0XFF3C8DCB), shape = RoundedCornerShape(50.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Select Subject",
                color = Color(0xFFFFFFFF),
                fontSize = 16.sp,
                fontFamily = AppFontFamily(),
                fontWeight = FontWeight.SemiBold
            )
        }

        if (showSubjectDialog){
            if (getPlatformName() == PLATFORM_DESKTOP || getPlatformName() == PLATFORM_WEB) {
                WebDialogContent(
                    onDismissRequest = { showSubjectDialog = false },
                    subjects = mSubjectList,mDoubtsAPIClient = mClient,searchQuery)
            } else {
                SubjectBottomSheet(
                    isBottomSheet = true,
                    onDismissRequest = { showSubjectDialog = false },
                    subjects = mSubjectList,
                    mDoubtsAPIClient = mClient,searchQuery
                )
            }
        }
    }
}
