package com.nameksolutions.regchain.curaorganization.responses

data class Address(
    val _id: String,
    val city: String,
    val district: String,
    val line: List<String>,
    val period: Period,
    val postalCode: String,
    val state: String,
    val text: String,
    val type: String,
    val use: String
)