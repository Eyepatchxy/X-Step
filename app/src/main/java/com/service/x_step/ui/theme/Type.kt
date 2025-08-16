package com.service.x_step.ui.theme

import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    titleLarge = TextStyle(
        fontSize = 50.sp,
        fontWeight = FontWeight.Bold,
        color = FontBlue,
        shadow = Shadow(
            color = Color.DarkGray,
            offset = Offset(4f, 4f),
            blurRadius = 5f
        )
    ),
    titleMedium = TextStyle(
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        color = FontBlue,
        shadow = Shadow(
            color = Color.DarkGray,
            offset = Offset(2f, 2f),
            blurRadius = 5f
        )
    ),

    bodyMedium = TextStyle(
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White
    ),
    bodySmall = TextStyle(
        fontSize = 20.sp,
        color = Color.White
    )

)

