package com.imobly.imobly.api.dto

import kotlinx.serialization.Serializable

@Serializable
class CreateReportDTO(
    val title: String,
    val message: String,
    val propertyId: String
)