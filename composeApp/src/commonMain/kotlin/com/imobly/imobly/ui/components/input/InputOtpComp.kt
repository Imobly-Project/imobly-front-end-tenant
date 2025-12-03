package com.imobly.imobly.ui.components.input

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imobly.imobly.ui.components.messageerror.MessageErrorComp
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.ui.theme.colors.TitleColor
import com.imobly.imobly.ui.theme.fonts.montserratFont

@Composable
fun InputOtpComp(
    label: String = "",
    otpLength: Int = 6,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    errorMessage: String = "",
    modifier: Modifier = Modifier.padding(16.dp)
) {
    Column(modifier = modifier) {

        if (label.isNotEmpty()) {
            Text(
                text = label,
                fontFamily = montserratFont(),
                fontSize = 15.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        BasicTextField(
            value = value,
            onValueChange = {
                if (it.length <= otpLength && it.all { c -> c.isDigit() }) {
                    onValueChange(it)
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword
            ),
            textStyle = LocalTextStyle.current.copy(
                fontSize = 22.sp,
                color = Color.Transparent
            ),
            singleLine = true,
            decorationBox = {

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    repeat(otpLength) { index ->
                        val char = when {
                            index < value.length -> value[index].toString()
                            else -> ""
                        }

                        val filledUntil = value.length
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .border(
                                    width = when {
                                        index == filledUntil -> 2.dp
                                        else -> 1.dp
                                    },
                                    color = when {
                                        index < filledUntil -> PrimaryColor
                                        index == filledUntil -> PrimaryColor
                                        else -> Color.LightGray
                                    },
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .background(Color.White, RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                        Text(
                            text = char,
                            fontSize = 22.sp,
                            fontFamily = montserratFont(),
                            color = TitleColor,
                            textAlign = TextAlign.Center
                        )
                        }

                        if (index < otpLength - 1) {
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                    }
                }
            }
        )

        if (isError && errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.padding(top = 4.dp))
            MessageErrorComp(errorMessage)
        }
    }
}
