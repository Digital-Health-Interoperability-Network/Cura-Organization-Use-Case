package com.nameksolutions.regchain.curaorganization.requests

data class Identifier(
    val assigner: String? = null,
    val partOf: String? = null,
    val period: Period? = null,
    val system: String? = null,
    val type: String? = null,
    val use: String? = null,
    val value: String? = null
)