package com.nameksolutions.regchain.curaorganization.requests

data class Qualification(
    val code: Type,
    val identifier: List<Identifier>,
    val issuer: Type,
    val period: Period
)