package com.nameksolutions.regchain.curaorganization.requests

data class NameRequest(
    val family: String? = null,
    val given: List<String>? = listOf(),
    val prefix: List<String>? = listOf(),
    val suffix: List<String>? = listOf(),
    val use: String? = null
)