package com.imobly.imobly.ui.components.messageerror

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imobly.imobly.ui.theme.fonts.montserratFont

@Composable
fun MessageErrorComp(message: String, fontSize: TextUnit = 12.sp) {
    Text(
        text = message,
        color = Color.Red,
        fontFamily = montserratFont(),
        fontSize = fontSize,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(5.dp)
    )
}