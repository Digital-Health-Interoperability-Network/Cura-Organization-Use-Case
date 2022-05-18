package com.nameksolutions.regchain.curaorganization.responses

data class AddresX(
    val _id: String,
    val city: String,
    val country: String,
    val district: String,
    val line: List<Any>,
    val period: PeriodX,
    val state: String,
    val type: String,
    val use: String
)