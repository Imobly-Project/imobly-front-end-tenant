package com.imobly.imobly.ui.components.input

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imobly.imobly.ui.components.messageerror.MessageErrorComp
import com.imobly.imobly.ui.theme.colors.BackGroundColor
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.ui.theme.fonts.montserratFont
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun InputComp(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    isNumeric: Boolean = false,
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    isError: Boolean = false,
    errorMessage: String = "",
    modifier: Modifier = Modifier.padding(16.dp).fillMaxWidth()
) {
    val numLines = if (singleLine) 1 else 4

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            label = {
                Text(label, fontFamily = montserratFont(), fontSize = 15.sp, fontWeight = FontWeight.ExtraBold)
            },
            value = value,
            onValueChange = onValueChange,
            shape = RoundedCornerShape(10.dp),
            singleLine = singleLine,
            minLines = numLines,
            maxLines = numLines,
            readOnly = readOnly,
            isError = isError,
            keyboardOptions = KeyboardOptions(keyboardType = if (isNumeric) KeyboardType.Number else KeyboardType.Text),
            placeholder = {
                Text(placeholder, fontFamily = montserratFont(), fontSize = 15.sp, fontWeight = FontWeight.ExtraBold)
            },
            textStyle = TextStyle(fontFamily = montserratFont(), fontSize = 15.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedTextColor = Color.Black,
                unfocusedLabelColor = PrimaryColor,
                unfocusedIndicatorColor = PrimaryColor,
                unfocusedContainerColor = BackGroundColor,
                focusedContainerColor = BackGroundColor,
                focusedTextColor = Color.Black,
                focusedLabelColor = PrimaryColor,
                focusedIndicatorColor = PrimaryColor,
                cursorColor = PrimaryColor
            )
        )
        if (isError) {
            MessageErrorComp(errorMessage)
        }
    }
}

@Preview
@Composable
fun InputCompPreview() {
    Box(
        Modifier.fillMaxWidth(0.8f),
        contentAlignment = Alignment.Center
    ) {
        InputComp(
            "Example",
            "Text for example",
            "",
            {}
        )
    }
}