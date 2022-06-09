package com.nameksolutions.regchain.curaorganization.responses

//data class for the response received when the organization logs in (the organization address details)
data class AddressLoginResponse(
    val _id: String,
    val city: String,
    val country: String,
    val district: String,
    val line: List<Any>,
    val period: PeriodAddressLoginResponse,
    val state: String,
    val type: String,
    val use: String
)