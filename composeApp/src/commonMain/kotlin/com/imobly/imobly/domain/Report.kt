package com.imobly.imobly.domain

import com.imobly.imobly.domain.enums.StatusReportEnum
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Report(
    val id: String? = null,
    val title: String = "",
    val message: String = "",
    val moment: String? = "",
    val status: StatusReportEnum = StatusReportEnum.NEW,
    val response: String = "",
    val tenant: Tenant = Tenant(),
    val property: Property = Property()
)