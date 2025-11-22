package com.imobly.imobly.ui.components.title

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imobly.imobly.ui.theme.colors.BackGroundColor
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.ui.theme.colors.TitleColor
import com.imobly.imobly.ui.theme.fonts.montserratFont
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun TitleComp(text: String, buttonBackAction: () -> Unit = {}, backButton: Boolean = false, fontSize: TextUnit = 20.sp) {

    Box(
        Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
    ) {

        if (backButton) {
            Button(
                modifier = Modifier
                    .width(60.dp)
                    .height(50.dp)
                    .background(
                        PrimaryColor,
                        shape = RoundedCornerShape(bottomEnd = 25.dp, topEnd = 25.dp)
                    ),
                onClick = buttonBackAction,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                elevation = ButtonDefaults.buttonElevation(0.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(Icons.Default.ArrowBackIosNew, "check")
            }
        }

        Box(modifier = Modifier.align(Alignment.Center)) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text,
                    fontSize = fontSize,
                    color = TitleColor,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = montserratFont()
                )
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth(0.4f),
                    thickness = 4.dp,
                    color = PrimaryColor
                )

            }
        }

    }
}

@Preview
@Composable
fun TitleCompPreview() {
    Box(
        Modifier.fillMaxSize().background(BackGroundColor),
        contentAlignment = Alignment.TopCenter
    ) {
        TitleComp("Exemplo", {})
    }
}