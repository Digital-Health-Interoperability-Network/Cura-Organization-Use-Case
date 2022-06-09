package com.nameksolutions.regchain.curaorganization.requests

data class Name(
    val family: String,
    val given: List<String>,
    val prefix: List<String>,
    val suffix: List<String>?
)