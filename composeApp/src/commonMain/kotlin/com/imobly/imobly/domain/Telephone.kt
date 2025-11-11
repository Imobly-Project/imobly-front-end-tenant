package com.imobly.imobly.domain

import kotlinx.serialization.Serializable

@Serializable
data class Telephone(
    var telephone1: String = "",
    var telephone2: String? = "",
    var telephone3: String? = ""
)