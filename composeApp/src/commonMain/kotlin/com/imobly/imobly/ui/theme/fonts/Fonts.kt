package com.imobly.imobly.ui.theme.fonts

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import imobly_front_end_tenant.composeapp.generated.resources.Montserrat_Italic_VariableFont_wght
import imobly_front_end_tenant.composeapp.generated.resources.Montserrat_VariableFont_wght
import imobly_front_end_tenant.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font

@Composable
fun montserratFont(): FontFamily {
    return FontFamily(
        Font(Res.font.Montserrat_VariableFont_wght)
    )
}

@Composable
fun montserratItalicFont(): FontFamily {
    return FontFamily(
        Font(Res.font.Montserrat_Italic_VariableFont_wght)
    )
}


