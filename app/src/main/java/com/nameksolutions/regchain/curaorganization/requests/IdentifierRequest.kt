package com.nameksolutions.regchain.curaorganization.requests

data class IdentifierRequest(
    val assigner: String? = null,
    val partOf: String? = null,
    val period: PeriodRequest? = null,
    val system: String? = null,
    val type: String? = null,
    val use: String? = null,
    val value: String? = null
)