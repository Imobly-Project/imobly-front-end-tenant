package com.imobly.imobly.domain

import kotlinx.serialization.Serializable

@Serializable
data class Payment(
    val id: String? = null,
    val lease: Lease = Lease(),
    val installments: List<MonthlyInstallment> = emptyList()
)