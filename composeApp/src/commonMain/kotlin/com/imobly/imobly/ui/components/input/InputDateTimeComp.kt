package com.imobly.imobly.ui.components.input

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Schedule
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
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun InputDateTimeComp(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    errorMessage: String = "",
    readOnly: Boolean = false,
    modifier: Modifier = Modifier.padding(16.dp).fillMaxWidth()
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }

    var tempDate by remember { mutableStateOf<LocalDate?>(null) }

    // Cores baseadas no estado readOnly
    val borderColor = when {
        readOnly -> DisabledColor
        isError || showError -> Color.Red
        else -> PrimaryColor
    }

    val labelColor = if (readOnly) DisabledColor else PrimaryColor
    val backgroundColor = if (readOnly) BackGroundColor.copy(alpha = 0.5f) else BackGroundColor
    val iconColor = if (readOnly) DisabledColor else PrimaryColor

    val placeholder = "DD/MM/AAAA HH:MM"
    val displayValue = value.ifEmpty { placeholder }
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
                        showDatePicker = true
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
                imageVector = if (value.isEmpty()) Icons.Filled.DateRange else Icons.Filled.Schedule,
                contentDescription = "Selecionar data e hora",
                tint = iconColor
            )
        }

        if (isError || showError) {
            MessageErrorComp(if (showError) "Data/Hora inválida" else errorMessage)
        }

        if (showDatePicker && !readOnly) {
            val datePickerState = rememberDatePickerState()

            DatePickerDialog(
                onDismissRequest = {
                    showDatePicker = false
                    showError = false
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val millis = datePickerState.selectedDateMillis
                            if (millis != null) {
                                tempDate = Instant.fromEpochMilliseconds(millis)
                                    .toLocalDateTime(TimeZone.UTC)
                                    .date

                                showDatePicker = false
                                showTimePicker = true
                                showError = false
                            } else {
                                showError = true
                            }
                        }
                    ) {
                        Text("PRÓXIMO", color = PrimaryColor, fontFamily = montserratFont())
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDatePicker = false
                            showError = false
                        }
                    ) {
                        Text("Cancelar", color = CancelColor, fontFamily = montserratFont())
                    }
                }
            ) {
                DatePicker(
                    state = datePickerState,
                    headline = {
                        Text(
                            text = "Selecione a Data",
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
                        containerColor = BackGroundColor,
                    )
                )
            }
        }

        if (showTimePicker && tempDate != null && !readOnly) {
            val timePickerState = rememberTimePickerState()

            AlertDialog(
                onDismissRequest = {
                    showTimePicker = false
                    tempDate = null
                    showError = false
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val selectedTime = LocalTime(
                                hour = timePickerState.hour,
                                minute = timePickerState.minute
                            )

                            val date = tempDate!!

                            val formatted =
                                "${date.day.toString().padStart(2, '0')}/" +
                                        "${date.month.number.toString().padStart(2, '0')}/" +
                                        "${date.year} " +
                                        "${selectedTime.hour.toString().padStart(2, '0')}:" +
                                        "${selectedTime.minute.toString().padStart(2, '0')}"

                            onValueChange(formatted)
                            showTimePicker = false
                            tempDate = null
                            showError = false
                        }
                    ) {
                        Text("OK", color = PrimaryColor, fontFamily = montserratFont())
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showTimePicker = false
                            tempDate = null
                            showError = false
                        }
                    ) {
                        Text("Cancelar", color = CancelColor, fontFamily = montserratFont())
                    }
                },
                title = { Text("Selecione a Hora", fontFamily = montserratFont(), color = PrimaryColor) },
                text = {
                    TimePicker(
                        state = timePickerState,
                        colors = TimePickerDefaults.colors(
                            containerColor = BackGroundColor,
                            selectorColor = PrimaryColor,
                            clockDialColor = PrimaryColor.copy(alpha = 0.2f),
                            periodSelectorSelectedContainerColor = PrimaryColor,
                            periodSelectorSelectedContentColor = Color.White
                        )
                    )
                },
                containerColor = BackGroundColor,
            )
        }
    }
}

@Preview
@Composable
fun InputDateTimeCompPreview() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var dateTimeValue by remember { mutableStateOf("") }
        InputDateTimeComp(
            label = "Agendar Visita",
            value = dateTimeValue,
            onValueChange = { dateTimeValue = it }
        )
    }
}