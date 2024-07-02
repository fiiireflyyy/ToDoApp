package com.fenix.todoapp.ui.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fenix.todoapp.ui.design.theme.BackElevatedDarkColor
import com.fenix.todoapp.ui.design.theme.BackPrimaryDarkColor
import com.fenix.todoapp.ui.design.theme.BackSecondaryDarkColor
import com.fenix.todoapp.ui.design.theme.ColorBlueDarkColor
import com.fenix.todoapp.ui.design.theme.ColorGrayDarkColor
import com.fenix.todoapp.ui.design.theme.ColorGrayLightDarkColor
import com.fenix.todoapp.ui.design.theme.ColorGreenDarkColor
import com.fenix.todoapp.ui.design.theme.ColorRedDarkColor
import com.fenix.todoapp.ui.design.theme.ColorWhiteDarkColor
import com.fenix.todoapp.ui.design.theme.LabelDisableDarkColor
import com.fenix.todoapp.ui.design.theme.LabelPrimaryDarkColor
import com.fenix.todoapp.ui.design.theme.LabelSecondaryDarkColor
import com.fenix.todoapp.ui.design.theme.LabelTertiaryDarkColor
import com.fenix.todoapp.ui.design.theme.SupportOverlayDarkColor
import com.fenix.todoapp.ui.design.theme.SupportSeparatorDarkColor



@Composable
fun DarkColorBlock(color: Color, text: String, textColor: Color = Color.White) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color)
            .padding(16.dp)
            .height(60.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Column {
            Text(
                text = text,
                color = textColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "#${Integer.toHexString(color.toArgb()).uppercase()}",
                color = textColor,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun DarkColorPalette() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD3D3D3))
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        DarkColorBlock(SupportSeparatorDarkColor, "Support [Dark] / Separator", )
        Spacer(modifier = Modifier.height(8.dp))
        DarkColorBlock(SupportOverlayDarkColor, "Support [Dark] / Overlay")
        Spacer(modifier = Modifier.height(8.dp))
        DarkColorBlock(LabelPrimaryDarkColor, "Label [Dark] / Primary", Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        DarkColorBlock(LabelSecondaryDarkColor, "Label [Dark] / Secondary", Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        DarkColorBlock(LabelTertiaryDarkColor, "Label [Dark] / Tertiary",Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        DarkColorBlock(LabelDisableDarkColor, "Label [Dark] / Disable",Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        DarkColorBlock(ColorRedDarkColor, "Color [Dark] / Red",Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        DarkColorBlock(ColorGreenDarkColor, "Color [Dark] / Green")
        Spacer(modifier = Modifier.height(8.dp))
        DarkColorBlock(ColorBlueDarkColor, "Color [Dark] / Blue")
        Spacer(modifier = Modifier.height(8.dp))
        DarkColorBlock(ColorGrayDarkColor, "Color [Dark] / Gray")
        Spacer(modifier = Modifier.height(8.dp))
        DarkColorBlock(ColorGrayLightDarkColor, "Color [Dark] / Gray Light")
        Spacer(modifier = Modifier.height(8.dp))
        DarkColorBlock(ColorWhiteDarkColor, "Color [Dark] / White",Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        DarkColorBlock(BackPrimaryDarkColor, "Back [Dark] / Primary")
        Spacer(modifier = Modifier.height(8.dp))
        DarkColorBlock(BackSecondaryDarkColor, "Back [Dark] / Secondary")
        Spacer(modifier = Modifier.height(8.dp))
        DarkColorBlock(BackElevatedDarkColor, "Back [Dark] / Elevated")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDarkColorPalette() {
    DarkColorPalette()
}
