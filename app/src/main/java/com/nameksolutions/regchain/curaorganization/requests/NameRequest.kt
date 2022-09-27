package com.nameksolutions.regchain.curaorganization.requests

data class NameRequest(
    val family: String,
    val given: List<String>,
    val prefix: List<String>,
    val suffix: List<String>,
    val use: String
)