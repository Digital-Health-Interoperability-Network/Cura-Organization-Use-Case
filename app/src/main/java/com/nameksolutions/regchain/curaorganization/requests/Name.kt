package com.nameksolutions.regchain.curaorganization.requests

data class Name(
    val family: String,
    val given: String,
    val prefix: String,
    val suffix: String
)