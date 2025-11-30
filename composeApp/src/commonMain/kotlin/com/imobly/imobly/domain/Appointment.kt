package com.imobly.imobly.domain

import kotlinx.serialization.Serializable

@Serializable
data class Appointment(
    val id: String? = null,
    val guestName: String = "",
    val moment: String = "",
    val telephone: String = "",
    val property: Property = Property()
)