package com.nameksolutions.regchain.curaorganization.responses

data class Identifier(
    val _id: String,
    val assigner: String,
    val partOf: String,
    val period: Period,
    val system: String,
    val type: String,
    val use: String,
    val value: String
)