package com.infinitylearn.doubt.mainscreenui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.infinitylearn.doubt.model.MyQuestionResponse
import com.infinitylearn.doubt.utils.AppFontFamily
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import doubts.composeapp.generated.resources.Res
import doubts.composeapp.generated.resources.ic_doubts_bookmarks_unselected
import doubts.composeapp.generated.resources.ic_maths_new
import org.jetbrains.compose.resources.painterResource

import org.jetbrains.compose.ui.tooling.preview.Preview


@Preview
@Composable
fun QuestionItem(questionData: MyQuestionResponse.QuestionData, platformName: String) {

    println("====================="+questionData.createdTime)
    Box(modifier = Modifier.padding(15.dp)) {
        Box(
            modifier = Modifier
                .background(
                    color = Color(0xffFCFCFC),
                )
                .border(width = 1.dp, color = Color(0xffD9DBE9), shape = RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .padding(5.dp)
        ) {


            Column {
                Row(
                    modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Image(
                        painter = painterResource(Res.drawable.ic_maths_new),
                        contentDescription = "",
                        modifier = Modifier.size(24.dp)
                    )

                    Text(
                        text = questionData.subjectName.toString(),
                        fontSize = 12.sp,
                        fontFamily = AppFontFamily(),
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )


                    FeedbackStatus(questionData)
                }

                QuestionTextImage(
                    questionData?.doubtText, questionData?.doubtImage, questionData?.isImageDoubt
                )


                TimeDateUI(questionData.createdTime)
            }

        }
    }
}

@Composable
fun FeedbackStatus(questionData: MyQuestionResponse.QuestionData) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 1.dp),

        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End
    ) {
        // Text equivalent to TextView with background and padding
        Text(
            text = questionData.doubtCardStatus.toString(), // Replace with actual string resource
            color = Color(0xffC4A200), // Text color
            fontSize = 10.sp, // Text size
            fontWeight = FontWeight.Bold, // Font weight to match Montserrat bold
            modifier = Modifier
                .background(
                    color = Color(0xffFFF7D4), // Background color tint
                    shape = RoundedCornerShape(8.dp) // Shape for background (rounded corners, customize as needed)
                )
                .padding(
                    horizontal = 10.dp, vertical = 4.dp
                ) // Padding equivalent to paddingHorizontal and paddingVertical
                .align(Alignment.CenterVertically) // Vertical alignment
        )

        // Spacer to provide some space between the Text and the Image
        Spacer(modifier = Modifier.width(8.dp))


        Image(

            painter = painterResource(Res.drawable.ic_doubts_bookmarks_unselected), // Replace with actual drawable
            contentDescription = null,
            modifier = Modifier
                .size(22.dp)
                .align(Alignment.CenterVertically),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun QuestionTextImage(
    questionText: String? = "", questionImageUrl: String? = "", imageDoubt: Boolean? = false
) {
    // Define the height of the item
    val itemHeight = 86.dp

    // Column to stack Text and Image vertically
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(itemHeight)
            .padding(top = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Text component for question text

        if (imageDoubt == true) {

            CoilImage(
                modifier = Modifier.fillMaxWidth(),
                imageModel = { questionImageUrl }, // loading a network image or local resource using an URL.
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )
            )

        } else {

            Text(
                text = questionText ?: "No Question Available",
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
}


@Composable
fun TimeDateUI(createdTimeMillis: String?) {

    createdTimeMillis?.let {
        Text(
            text = createdTimeMillis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp),
            color = Color(0xFF14142B), // Replace with your color resource
            fontSize = 12.sp,
            maxLines = 3,
            fontFamily = AppFontFamily(),
            fontWeight = FontWeight.SemiBold // Replace with your font family if needed
        )
    } ?: run {
        Text(text = "Unknown Date")
    }

}

