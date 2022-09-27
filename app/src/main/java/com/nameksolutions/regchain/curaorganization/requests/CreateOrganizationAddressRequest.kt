package com.nameksolutions.regchain.curaorganization.requests

data class CreateOrganizationAddressRequest(
    val city: String? = null,
    val district: String? = null,
    val line: List<String>? = null,
    val period: PeriodRequest? = null,
    val postalCode: String? = null,
    val state: String? = null,
    val text: String? = null,
    val type: String? = null,
    val use: String? = null,
    val country: String? = null
)