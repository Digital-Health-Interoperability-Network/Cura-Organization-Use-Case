package com.nameksolutions.regchain.curaorganization.requests

data class Identifier(
    val assigner: String,
    val partOf: String,
    val period: Period,
    val system: String,
    val type: String,
    val use: String,
    val value: String
)