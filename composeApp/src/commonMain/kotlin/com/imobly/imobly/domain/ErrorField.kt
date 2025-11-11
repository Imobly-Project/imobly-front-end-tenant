package com.imobly.imobly.domain

import kotlinx.serialization.Serializable

@Serializable
data class ErrorField(
    val name: String,
    val description: String,
    val value: String? = null
)