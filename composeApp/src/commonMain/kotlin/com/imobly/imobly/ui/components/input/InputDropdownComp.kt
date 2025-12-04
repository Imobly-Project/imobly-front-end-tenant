package com.imobly.imobly.ui.components.input

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imobly.imobly.ui.components.messageerror.MessageErrorComp
import com.imobly.imobly.ui.theme.colors.BackGroundColor
import com.imobly.imobly.ui.theme.colors.DisabledColor
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.ui.theme.fonts.montserratFont
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputDropdownComp(
    label: String,
    options: Map<String, String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    isError: Boolean = false,
    errorMessage: String = "",
    readOnly: Boolean = false,
    isEnabled: Boolean = true,
    modifier: Modifier = Modifier.padding(16.dp).fillMaxWidth()
) {
    var expanded by remember { mutableStateOf(false) }

    // Cores baseadas no estado readOnly
    val textColor = if (readOnly) DisabledColor else Color.Black
    val labelColor = if (readOnly) DisabledColor else PrimaryColor
    val indicatorColor = if (readOnly) DisabledColor else PrimaryColor
    val backgroundColor = if (readOnly) BackGroundColor.copy(alpha = 0.5f) else BackGroundColor

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            if (isEnabled && !readOnly) expanded = !expanded
        }
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            enabled = !readOnly,
            label = {
                Text(
                    label,
                    fontFamily = montserratFont(),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = labelColor
                )
            },
            shape = RoundedCornerShape(10.dp),
            textStyle = TextStyle(
                fontFamily = montserratFont(),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            ),
            modifier = modifier
                .fillMaxWidth()
                .menuAnchor(),  // Corrigido: Removido os parâmetros obsoletos
            colors = TextFieldDefaults.colors(
                unfocusedTextColor = textColor,
                unfocusedLabelColor = labelColor,
                unfocusedIndicatorColor = indicatorColor,
                unfocusedContainerColor = backgroundColor,
                focusedContainerColor = backgroundColor,
                focusedTextColor = textColor,
                focusedLabelColor = labelColor,
                focusedIndicatorColor = indicatorColor,
                cursorColor = if (readOnly) Color.Transparent else PrimaryColor,
                disabledTextColor = DisabledColor,
                disabledLabelColor = DisabledColor,
                disabledIndicatorColor = DisabledColor,
                disabledContainerColor = BackGroundColor.copy(alpha = 0.5f)
            ),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            }
        )

        if (isError) {
            MessageErrorComp(errorMessage)
        }

        ExposedDropdownMenu(
            expanded = expanded && isEnabled && !readOnly,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            option.key,
                            fontFamily = montserratFont(),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    onClick = {
                        onOptionSelected(option.value)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun InputDropdownCompPreview() {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        var selected by remember { mutableStateOf("SINGLE") }
        InputDropdownComp(
            label = "Estado Civil",
            options = mapOf(
                Pair("MARRIED", "1"),
                Pair("SINGLE", "2"),
                Pair("WIDOWED", "3"),
                Pair("DIVORCED", "3")
            ),
            selectedOption = selected,
            onOptionSelected = { selected = it }
        )
    }
}