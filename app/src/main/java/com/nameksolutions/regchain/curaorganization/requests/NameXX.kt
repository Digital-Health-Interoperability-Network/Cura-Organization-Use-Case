package com.nameksolutions.regchain.curaorganization.requests

data class NameXX(
    val city: String,
    val district: String,
    val line: List<String>,
    val period: PeriodXX,
    val postalCode: String,
    val state: String,
    val text: String,
    val type: String,
    val use: String
)