package com.imobly.imobly.ui.components.input

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imobly.imobly.ui.components.messageerror.MessageErrorComp
import com.imobly.imobly.ui.theme.colors.BackGroundColor
import com.imobly.imobly.ui.theme.colors.IconColor
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.ui.theme.fonts.montserratFont
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Composable
fun InputDateComp(
    label: String ,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    errorMessage: String = "",
    readOnly: Boolean = false,
    modifier: Modifier = Modifier.padding(16.dp).fillMaxWidth()
) {
    var showCalendar by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
    ) {
        Text(
            label,
            fontFamily = montserratFont(),
            fontSize = 15.sp,
            fontWeight = FontWeight.ExtraBold,
            color = PrimaryColor,
            modifier = Modifier.padding(horizontal = 15.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .border(1.dp, PrimaryColor, RoundedCornerShape(12.dp))
                .background(BackGroundColor, RoundedCornerShape(12.dp))
                .clickable { showCalendar = !readOnly }
                .padding(horizontal = 13.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = value.ifEmpty { "Selecionar data" },
                    fontFamily = montserratFont(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Icon(
                    imageVector = Icons.Filled.CalendarToday,
                    contentDescription = "Abrir calendário",
                    tint = Color.Black
                )
            }
        }

        if (isError) {
            MessageErrorComp(errorMessage)
        }
    }

    if (showCalendar) {
        DatePickerDialogCustom(
            onDismiss = { showCalendar = false },
            onDateSelected = {
                onValueChange(it)
                showCalendar = false
            }
        )
    }
}

@OptIn(ExperimentalTime::class)
@Composable
fun DatePickerDialogCustom(
    onDismiss: () -> Unit,
    onDateSelected: (String) -> Unit
) {
    val now = remember { Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()) }
    val today = now.date
    var currentMonth by remember { mutableStateOf(today.monthNumber) }
    var currentYear by remember { mutableStateOf(today.year) }

    val daysInMonth = getDaysInMonth(currentMonth, currentYear)
    val firstDayOfWeek = LocalDate(currentYear, currentMonth, 1).dayOfWeek.ordinal
    val days = (1..daysInMonth).toList()
    val grid = (1..(firstDayOfWeek + days.size)).map { idx ->
        if (idx <= firstDayOfWeek) null else days[idx - firstDayOfWeek - 1]
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    if (currentMonth == 1) {
                        currentMonth = 12
                        currentYear -= 1
                    } else currentMonth -= 1
                }) { Text("<", fontSize = 20.sp) }

                Text(
                    text = "${getMonthName(currentMonth)} $currentYear",
                    fontFamily = montserratFont(),
                    fontWeight = FontWeight.Bold
                )

                IconButton(onClick = {
                    if (currentMonth == 12) {
                        currentMonth = 1
                        currentYear += 1
                    } else currentMonth += 1
                }) { Text(">", fontSize = 20.sp) }
            }
        },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    listOf("D", "S", "T", "Q", "Q", "S", "S").forEach { dayLabel ->
                        Text(
                            text = dayLabel,
                            fontFamily = montserratFont(),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Column {
                    grid.chunked(7).forEach { week ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            week.forEach { day ->
                                if (day != null) {
                                    val isToday =
                                        day == today.day && currentMonth == today.month.number && currentYear == today.year

                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .background(
                                                if (isToday) PrimaryColor.copy(alpha = 0.2f)
                                                else Color.Transparent,
                                                RoundedCornerShape(50)
                                            )
                                            .clickable {
                                                val formatted =
                                                    "${day.toString().padStart(2, '0')}/" +
                                                            "${currentMonth.toString().padStart(2, '0')}/$currentYear"
                                                onDateSelected(formatted)
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = day.toString(),
                                            textAlign = TextAlign.Center,
                                            fontFamily = montserratFont(),
                                            fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal,
                                            color = if (isToday) PrimaryColor else Color.Black
                                        )
                                    }
                                } else {
                                    Spacer(modifier = Modifier.size(36.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}


fun getDaysInMonth(month: Int, year: Int): Int = when (month) {
    1, 3, 5, 7, 8, 10, 12 -> 31
    4, 6, 9, 11 -> 30
    2 -> if (isLeapYear(year)) 29 else 28
    else -> 30
}

fun isLeapYear(year: Int): Boolean =
    (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)

fun getMonthName(month: Int): String =
    listOf(
        "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
        "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
    )[month - 1]

@Preview
@Composable
fun InputDateCompPreview() {
    Box(
        Modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        InputDateComp(
            label = "Data de início",
            value = "",
            onValueChange = {}
        )
    }
}
