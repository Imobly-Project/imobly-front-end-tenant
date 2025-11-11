package com.imobly.imobly.ui.components.input

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imobly.imobly.ui.components.messageerror.MessageErrorComp
import com.imobly.imobly.ui.theme.colors.BackGroundColor
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.ui.theme.fonts.montserratFont
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun InputPasswordComp(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    passwordVisible: Boolean = false,
    changePasswordVisible: () -> Unit,
    readOnly: Boolean = false,
    isError: Boolean = false,
    errorMessage: String = "",
    modifier: Modifier = Modifier.padding(16.dp).fillMaxWidth()
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {
        OutlinedTextField(
            label = {
                Text(label, fontFamily = montserratFont(), fontSize = 15.sp, fontWeight = FontWeight.ExtraBold)
            },
            onValueChange = { onValueChange(it) },
            value = value,
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            placeholder = {
                Text(placeholder, fontFamily = montserratFont(), fontSize = 15.sp, fontWeight = FontWeight.ExtraBold)
            },
            readOnly = readOnly,
            isError = isError,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            textStyle = TextStyle(fontFamily = montserratFont(), fontSize = 15.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                Icon(
                    imageVector = if (passwordVisible) {
                        Icons.Filled.Visibility
                    } else {
                        Icons.Filled.VisibilityOff
                    },
                    contentDescription = "Trocar visibildade da senha",
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { changePasswordVisible() },
                )
            },
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
fun InputPasswordCompPreview() {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        InputPasswordComp(
            "Example",
            "Text for example",
            "",
            {},
            false,
            {}
        )
    }
}