package com.nameksolutions.regchain.curaorganization.responses

data class NameResponse(
    val _id: String,
    val family: String,
    val given: List<String>,
    val prefix: List<String>,
    val suffix: List<String>,
    val use: String
)