package com.imobly.imobly.ui.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imobly.imobly.ui.theme.colors.BackGroundColor
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.ui.theme.colors.TitleColor
import com.imobly.imobly.ui.theme.fonts.montserratFont

@Composable
fun CardButtonComp(
    text: String,
    width: Dp = 200.dp,
    icon : @Composable ()-> Unit,
    backgroundColor: Color = BackGroundColor,
    highlightColor:Color = PrimaryColor,
    action: ()-> Unit
){
    OutlinedCard(
        modifier = Modifier
            .width(width)
            .height(100.dp),
        onClick = action,

        ){
        Row(){
            Column(
                Modifier.background(highlightColor)
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                icon()
            }

            Column(
                Modifier.background(backgroundColor)
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = text,
                    textAlign = TextAlign.Center,
                    fontFamily = montserratFont(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = TitleColor
                )
            }
        }
    }
}