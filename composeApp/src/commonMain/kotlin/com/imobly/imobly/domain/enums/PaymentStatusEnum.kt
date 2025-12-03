package com.imobly.imobly.domain.enums

enum class PaymentStatusEnum(val label: String) {
    PAID("Pago"),
    PENDING("Pendente"),
    OVERDUE("Vencido");

    override fun toString(): String = label
}