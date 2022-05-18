package com.nameksolutions.regchain.curaorganization.requests

data class AddressRequest(
    val city: String,
    val country: String,
    val district: String,
    val period: Period,
    val state: String,
    val type: String,
    val use: String
)