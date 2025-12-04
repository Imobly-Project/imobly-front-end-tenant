package com.imobly.imobly.ui.components.input

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imobly.imobly.ui.components.messageerror.MessageErrorComp
import com.imobly.imobly.ui.theme.colors.BackGroundColor
import com.imobly.imobly.ui.theme.colors.CancelColor
import com.imobly.imobly.ui.theme.colors.DisabledColor
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.ui.theme.fonts.montserratFont
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun InputDateComp(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    errorMessage: String = "",
    readOnly: Boolean = false,
    modifier: Modifier = Modifier.padding(16.dp).fillMaxWidth()
) {
    var showPicker by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }

    // Cores baseadas no estado readOnly
    val borderColor = when {
        readOnly -> DisabledColor
        isError || showError -> Color.Red
        else -> PrimaryColor
    }

    val labelColor = if (readOnly) DisabledColor else PrimaryColor
    val backgroundColor = if (readOnly) BackGroundColor.copy(alpha = 0.5f) else BackGroundColor
    val iconColor = if (readOnly) DisabledColor else PrimaryColor

    val displayValue = value.ifEmpty { "DD/MM/AAAA" }
    val contentColor = when {
        readOnly -> DisabledColor
        value.isEmpty() -> PrimaryColor.copy(alpha = 0.5f)
        else -> Color.Black
    }

    Column(modifier = modifier) {
        Text(
            text = label,
            fontFamily = montserratFont(),
            fontSize = 15.sp,
            fontWeight = FontWeight.ExtraBold,
            color = labelColor,
            modifier = Modifier.padding(start = 12.dp, bottom = 4.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(backgroundColor)
                .border(1.dp, borderColor, RoundedCornerShape(10.dp))
                .clickable(enabled = !readOnly) {
                    if (!readOnly) {
                        showPicker = true
                    }
                }
                .padding(
                    vertical = 16.dp,
                    horizontal = 16.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = displayValue,
                color = contentColor,
                fontFamily = montserratFont(),
                fontSize = 15.sp,
                fontWeight = if (value.isEmpty()) FontWeight.ExtraBold else FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.Filled.DateRange,
                contentDescription = "Selecionar data",
                tint = iconColor
            )
        }

        if (isError || showError) {
            MessageErrorComp(if (showError) "Data inválida" else errorMessage)
        }

        if (showPicker && !readOnly) {
            val datePickerState = rememberDatePickerState()

            DatePickerDialog(
                onDismissRequest = {
                    showPicker = false
                    showError = false
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val millis = datePickerState.selectedDateMillis
                            if (millis != null) {
                                val date = Instant.fromEpochMilliseconds(millis)
                                    .toLocalDateTime(TimeZone.UTC)
                                    .date

                                val formatted =
                                    "${date.day.toString().padStart(2, '0')}/" +
                                            "${date.month.number.toString().padStart(2, '0')}/" +
                                            "${date.year}"

                                onValueChange(formatted)
                                showPicker = false
                                showError = false
                            } else {
                                showError = true
                            }
                        }
                    ) {
                        Text("OK", color = PrimaryColor, fontFamily = montserratFont())
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showPicker = false
                            showError = false
                        }
                    ) {
                        Text("Cancelar", color = CancelColor, fontFamily = montserratFont())
                    }
                },
                colors = DatePickerDefaults.colors(
                    containerColor = BackGroundColor
                )
            ) {
                DatePicker(
                    state = datePickerState,
                    headline = {
                        Text(
                            text = label,
                            color = PrimaryColor,
                            fontFamily = montserratFont(),
                            modifier = Modifier.padding(16.dp)
                        )
                    },
                    colors = DatePickerDefaults.colors(
                        todayContentColor = PrimaryColor,
                        todayDateBorderColor = PrimaryColor,
                        selectedDayContentColor = Color.White,
                        selectedDayContainerColor = PrimaryColor,
                        dayContentColor = PrimaryColor,
                        containerColor = BackGroundColor,
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun InputDateCompPreview() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var dateValue by remember { mutableStateOf("") }
        InputDateComp(
            label = "Data de Nascimento",
            value = dateValue,
            onValueChange = { dateValue = it }
        )
    }
}