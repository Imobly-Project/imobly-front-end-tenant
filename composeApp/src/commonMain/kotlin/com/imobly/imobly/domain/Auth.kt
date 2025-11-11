package com.imobly.imobly.domain

import kotlinx.serialization.Serializable

@Serializable
data class Auth(
    val email: String,
    val password: String
)