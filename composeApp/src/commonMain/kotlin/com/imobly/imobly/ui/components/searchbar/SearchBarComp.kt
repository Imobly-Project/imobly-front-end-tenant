package com.imobly.imobly.ui.components.searchbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imobly.imobly.ui.theme.fonts.montserratFont
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce

@OptIn(FlowPreview::class)
@Composable
fun SearchBarComp(label: String, value: String, onValueChange: (String) -> Unit, searchAction: () -> Unit) {
    LaunchedEffect(value) {
        snapshotFlow { value }
            .debounce(600)
            .collect { searchAction() }
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                label,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                fontFamily = montserratFont()
            )
        },
        trailingIcon = {
            Icon(Icons.Default.Search, "Lupa")
        },
        modifier = Modifier
            .widthIn(max = 600.dp)
            .fillMaxWidth()
            .padding(10.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFFFFFFF),
            unfocusedContainerColor = Color(0xFFFFFFFF),
            focusedIndicatorColor = Color(0xFFF2603F),
            unfocusedIndicatorColor = Color(0xFFE0E0E0)
        ),
        shape = RoundedCornerShape(30.dp)
    )
}