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
import com.fenix.todoapp.ui.design.theme.BackElevatedLightColor
import com.fenix.todoapp.ui.design.theme.BackPrimaryLightColor
import com.fenix.todoapp.ui.design.theme.BackSecondaryLightColor
import com.fenix.todoapp.ui.design.theme.ColorBlueLightColor
import com.fenix.todoapp.ui.design.theme.ColorGrayLightColor
import com.fenix.todoapp.ui.design.theme.ColorGrayLightLightColor
import com.fenix.todoapp.ui.design.theme.ColorGreenLightColor
import com.fenix.todoapp.ui.design.theme.ColorRedLightColor
import com.fenix.todoapp.ui.design.theme.ColorWhiteLightColor
import com.fenix.todoapp.ui.design.theme.LabelDisableLightColor
import com.fenix.todoapp.ui.design.theme.LabelPrimaryLightColor
import com.fenix.todoapp.ui.design.theme.LabelSecondaryLightColor
import com.fenix.todoapp.ui.design.theme.LabelTertiaryLightColor
import com.fenix.todoapp.ui.design.theme.SupportOverlayLightColor
import com.fenix.todoapp.ui.design.theme.SupportSeparatorLightColor

@Composable
fun LightColorBlock(color: Color, text: String) {
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
                color = if (text == "Label [Light] / Primary") Color.White else Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "#${Integer.toHexString(color.toArgb()).uppercase()}",
                color = if (text == "Label [Light] / Primary") Color.White else Color.Black,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun LightColorPalette() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD3D3D3))
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        LightColorBlock(SupportSeparatorLightColor, "Support [Light] / Separator")
        Spacer(modifier = Modifier.height(8.dp))
        LightColorBlock(SupportOverlayLightColor, "Support [Light] / Overlay")
        Spacer(modifier = Modifier.height(8.dp))
        LightColorBlock(LabelPrimaryLightColor, "Label [Light] / Primary")
        Spacer(modifier = Modifier.height(8.dp))
        LightColorBlock(LabelSecondaryLightColor, "Label [Light] / Secondary")
        Spacer(modifier = Modifier.height(8.dp))
        LightColorBlock(LabelTertiaryLightColor, "Label [Light] / Tertiary")
        Spacer(modifier = Modifier.height(8.dp))
        LightColorBlock(LabelDisableLightColor, "Label [Light] / Disable")
        Spacer(modifier = Modifier.height(8.dp))
        LightColorBlock(ColorRedLightColor, "Color [Light] / Red")
        Spacer(modifier = Modifier.height(8.dp))
        LightColorBlock(ColorGreenLightColor, "Color [Light] / Green")
        Spacer(modifier = Modifier.height(8.dp))
        LightColorBlock(ColorBlueLightColor, "Color [Light] / Blue")
        Spacer(modifier = Modifier.height(8.dp))
        LightColorBlock(ColorGrayLightColor, "Color [Light] / Gray")
        Spacer(modifier = Modifier.height(8.dp))
        LightColorBlock(ColorGrayLightLightColor, "Color [Light] / Gray Light")
        Spacer(modifier = Modifier.height(8.dp))
        LightColorBlock(ColorWhiteLightColor, "Color [Light] / White")
        Spacer(modifier = Modifier.height(8.dp))
        LightColorBlock(BackPrimaryLightColor, "Back [Light] / Primary")
        Spacer(modifier = Modifier.height(8.dp))
        LightColorBlock(BackSecondaryLightColor, "Back [Light] / Secondary")
        Spacer(modifier = Modifier.height(8.dp))
        LightColorBlock(BackElevatedLightColor, "Back [Light] / Elevated")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLightColorPalette() {
    LightColorPalette()
}
