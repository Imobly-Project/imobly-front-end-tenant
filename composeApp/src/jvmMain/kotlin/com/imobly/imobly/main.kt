package com.imobly.imobly

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "imobly-front-end-tenant",
    ) {
        App()
    }
}