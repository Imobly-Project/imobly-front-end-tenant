package com.imobly.imobly.domain

import kotlinx.serialization.Serializable

@Serializable
data class Lease(
    val id: String? = null,
    val startDate: String = "",
    val endDate: String = "",
    val createdAt: String = "",
    val lastUpdatedAt: String = "",
    var property: Property = Property(),
    var tenant: Tenant = Tenant(),
    val durationInMonths: String = "",
    val monthlyRent: String = "",
    val securityDeposit: String = "",
    val paymentDueDay: String = "",
    val isEnabled: Boolean = false
)