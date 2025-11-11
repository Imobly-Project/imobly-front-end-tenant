package com.imobly.imobly.ui.components.clickable

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun ClickableComp(onClick: () -> Unit, content: @Composable () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = Color.Black),
    ) {
        content()
    }
}