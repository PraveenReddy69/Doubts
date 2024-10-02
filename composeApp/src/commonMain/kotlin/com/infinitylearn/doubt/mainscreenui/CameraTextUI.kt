package com.infinitylearn.doubt.mainscreenui


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text


import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp

import androidx.compose.ui.unit.sp
import com.infinitylearn.doubt.Platform
import com.infinitylearn.doubt.mainscreenui.model.UIConfig
import com.infinitylearn.doubt.utils.AppFontFamily
import com.infinitylearn.doubt.utils.PLATFORM_DESKTOP
import com.infinitylearn.doubt.utils.hexToColor


import doubts.composeapp.generated.resources.Res
import doubts.composeapp.generated.resources.ic_doubts_camera
import doubts.composeapp.generated.resources.iv_question_icon
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Preview
@Composable
fun CameraTextUI(platform: String,
                 uiConfig: UIConfig?,
                 cameraTextUIActions: CameraTextUIActions = CameraTextUIActions()
) {

    val gradientBrush = Brush.linearGradient(
        colors = listOf(Color(0xFFFFFBEE), Color(0xFFFFEDAA))
    )
    Box(modifier = Modifier.background(brush = gradientBrush).fillMaxWidth()) {
        Image(
            painter = painterResource(Res.drawable.iv_question_icon),
            contentDescription = "",
            alignment = Alignment.TopEnd,
            modifier = Modifier.fillMaxWidth().size(100.dp).alpha(0.5f),
        )
        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {

            Text(
                text = uiConfig?.title?.text ?: "Ask a Question",
                color = Color(0xFFC4A200),
                fontSize = 20.sp,
                fontFamily = AppFontFamily(),
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.padding(vertical = 5.dp))

            println("----------------------->" + platform.toString())
            if (!platform.equals(PLATFORM_DESKTOP)) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White, shape = RoundedCornerShape(5.dp))
                        .border(width = 1.dp, color = Color(0xFFFBD323))
                        .padding(vertical = 5.dp)
                        .clickable(indication = null, interactionSource =  remember { MutableInteractionSource() } ) { cameraTextUIActions.navigateToCamera() },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(modifier = Modifier.padding(vertical = 10.dp))

                    Image(
                        painter = painterResource(Res.drawable.ic_doubts_camera),
                        contentDescription = ""
                    )

                    Text(
                        text = "Click/ Upload photo",
                        color = Color(0xFFC4A200),
                        fontSize = 14.sp,
                        fontFamily = AppFontFamily(),
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                }

                Text(
                    text = "or",
                    color = Color(0xFF4E4B66),
                    fontSize = 14.sp,
                    fontFamily = AppFontFamily(),
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .align(alignment = Alignment.CenterHorizontally)
                )
            }


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White, shape = RoundedCornerShape(5.dp))
                    .border(width = 1.dp, color = hexToColor(uiConfig?.title?.strokeColor ?: "#C4A200"))
                    .padding(vertical = 5.dp, horizontal = 10.dp).clickable(indication = null, interactionSource =  remember { MutableInteractionSource() } ) { cameraTextUIActions.navigateToText() },
                horizontalAlignment = Alignment.Start
            ) {


                Text(
                    text = "Type here…",
                    color = Color(0xFFA0A3BD),
                    fontSize = 14.sp,
                    fontFamily = AppFontFamily(),
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }


        }
    }
}

data class CameraTextUIActions(
    var navigateToCamera: () -> Unit = {},
    var navigateToText: () -> Unit = {},
)

