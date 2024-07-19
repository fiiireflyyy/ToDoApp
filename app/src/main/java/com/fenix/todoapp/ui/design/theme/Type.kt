package com.fenix.todoapp.ui.design.theme


import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    titleLarge = TextStyle(
        fontWeight = FontWeight(500),
        fontSize = 28.sp,
        lineHeight = 32.sp,
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight(500),
        fontSize = 20.sp,
        lineHeight = 32.sp,
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight(400),
        fontSize = 16.sp,
        lineHeight = 20.sp,
    ),
    bodySmall = TextStyle(
        fontWeight = FontWeight(400),
        fontSize = 16.sp,
        lineHeight = 20.sp,
        textDecoration = TextDecoration.LineThrough,
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight(500),
        fontSize = 14.sp,
        lineHeight = 24.sp,
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight(400),
        fontSize = 14.sp,
        lineHeight = 20.sp,
    )
)