package com.imobly.imobly.api.dto

import com.imobly.imobly.domain.ErrorField
import kotlinx.serialization.Serializable

@Serializable
data class ErrorDTO(
    val code: String = "",
    val status: Int = 500,
    val message: String = "",
    val timestamp: String = "",
    val path: String = "",
    val errorFields: List<ErrorField>? = null
): ResponseMessage()