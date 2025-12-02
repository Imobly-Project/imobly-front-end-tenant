package com.imobly.imobly.api.dto

import com.imobly.imobly.domain.enums.PaymentStatusEnum
import kotlinx.serialization.Serializable

@Serializable
data class PaymentStatusDTO (
    val status: PaymentStatusEnum
)