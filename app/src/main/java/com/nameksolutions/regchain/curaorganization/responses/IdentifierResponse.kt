package com.nameksolutions.regchain.curaorganization.responses

data class IdentifierResponse(
    val _id: String,
    val assigner: String,
    val partOf: String,
    val period: PeriodResponse,
    val system: String,
    val type: String,
    val use: String,
    val value: String
)