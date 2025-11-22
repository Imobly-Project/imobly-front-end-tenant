package com.imobly.imobly.ui.components.input

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
    isEnabled: Boolean = true,
    modifier: Modifier = Modifier.padding(16.dp).fillMaxWidth()
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            if (isEnabled) expanded = !expanded
        }

    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            label = {
                Text(
                    label,
                    fontFamily = montserratFont(),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            },
            shape = RoundedCornerShape(10.dp),
            textStyle = TextStyle(
                fontFamily = montserratFont(),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable, isEnabled),
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
            ),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            }
        )

        if (isError) {
            MessageErrorComp(errorMessage)
        }

        ExposedDropdownMenu(
            expanded = expanded && isEnabled,
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