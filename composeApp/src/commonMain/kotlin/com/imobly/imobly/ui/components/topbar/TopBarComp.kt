package com.imobly.imobly.ui.components.topbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import imobly_front_end_tenant.composeapp.generated.resources.Res
import imobly_front_end_tenant.composeapp.generated.resources.image_logo
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TopBarComp() {
    Box(
        Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(5.dp),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            repeat(3) {
                Box(modifier = Modifier.padding(vertical = 2.dp, horizontal = 10.dp)) {
                    Box(
                        Modifier
                            .width(35.dp)
                            .height(5.dp)
                            .background(
                                PrimaryColor,
                                shape = RoundedCornerShape(1.dp)
                            )
                    )
                }
            }
        }

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(Res.drawable.image_logo),
                contentDescription = "Logo",
                modifier = Modifier.size(100.dp)
            )
        }
    }
}


@Preview
@Composable
fun TopBarCompPreview() {
    Box(
        Modifier.fillMaxSize().background(Color.White),
        contentAlignment = Alignment.TopCenter
    ) {
        TopBarComp()
    }
}
