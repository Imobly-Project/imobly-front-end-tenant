package com.imobly.imobly.domain.enums

enum class StatusReportEnum(val description: String) {
    RESOLVED("Resolvido"),
    PENDING("Pendente"),
    NEW("Novo"),
    CLOSED("Fechado");

    override fun toString(): String = description
}