package com.infinitylearn.doubt.mainscreenui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.infinitylearn.doubt.utils.AppFontFamily
import doubts.composeapp.generated.resources.Res
import doubts.composeapp.generated.resources.ic_doubts_bookmarks_unselected
import doubts.composeapp.generated.resources.ic_doubts_filters_unselected
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Preview
@Composable
fun MyQuestionUI() {

    Box(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxWidth()
    ) {

        Column {

            Row(
                modifier = Modifier.padding(vertical = 15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "My Questions",
                    color = Color(0xFF14142B),
                    fontSize = 16.sp,
                    fontFamily = AppFontFamily(),
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )

                Row(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .weight(1f),
                    horizontalArrangement = Arrangement.End
                ) {
                    Image(
                        painter = painterResource(Res.drawable.ic_doubts_bookmarks_unselected),
                        contentDescription = ""
                    )

                    Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                    Image(
                        painter = painterResource(Res.drawable.ic_doubts_filters_unselected),
                        contentDescription = ""
                    )

                }


            }
        }
    }
}