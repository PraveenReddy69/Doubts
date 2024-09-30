package com.infinitylearn.doubt.mainscreenui



import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.infinitylearn.doubt.utils.AppFontFamily
import doubts.composeapp.generated.resources.Res
import doubts.composeapp.generated.resources.ic_no_doubts
import org.jetbrains.compose.resources.painterResource


@Composable
fun NoQuestionsFound(onResetFiltersClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Image
        Image(
            painter = painterResource(Res.drawable.ic_no_doubts),
            contentDescription = null,
            modifier = Modifier
                .size(162.dp, 168.dp)
                .padding(top = 4.dp)
        )

        // Title
        Text(
            text = "No Questions found",
            fontFamily = AppFontFamily(),
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF4E4B66), // Replace with your color resource
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 10.dp)
        )

        // Description
        Text(
            text = "Try adjusting your filters to find questions you are looking for",
            fontFamily = AppFontFamily(),
            fontWeight = FontWeight.Medium,
            color = Color(0xFF6E7191), // Replace with your color resource
            fontSize = 14.sp,
            modifier = Modifier
                .padding(top = 10.dp)
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally)
        )

        // Reset Filters Button
        Text(
            text = "Reset filters",
            fontFamily = AppFontFamily(),
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF3C8DCB), // Replace with your button text color
            fontSize = 14.sp,
            modifier = Modifier.padding(vertical = 5.dp).clickable {
                onResetFiltersClick()
            }
        )
    }
}
