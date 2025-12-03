package com.imobly.imobly.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class ResetPasswordDTO(
    val email: String,

    val code:String,

    val newPassword: String
)