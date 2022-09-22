package com.nameksolutions.regchain.curaorganization.requests

data class Telecom(
    val active: Boolean,
    val rank: Int,
    val system: String,
    val use: String,
    val value: String
)