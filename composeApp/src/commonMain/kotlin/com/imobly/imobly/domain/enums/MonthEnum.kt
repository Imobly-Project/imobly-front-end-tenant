package com.imobly.imobly.domain.enums

enum class MonthEnum(val label: String) {
    JANUARY("Janeiro"),
    FEBRUARY("Fevereiro"),
    MARCH("Março"),
    APRIL("Abril"),
    MAY("Maio"),
    JUNE("Junho"),
    JULY("Julho"),
    AUGUST("Agosto"),
    SEPTEMBER("Setembro"),
    OCTOBER("Outubro"),
    NOVEMBER("Novembro"),
    DECEMBER("Dezembro");

    override fun toString(): String = label

}