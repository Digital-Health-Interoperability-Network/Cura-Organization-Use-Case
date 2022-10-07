package com.nameksolutions.regchain.curaorganization.responses

data class NameResponse(
    val _id: String = "",
    val family: String = "",
    val given: List<String> = listOf<String>(),
    val prefix: List<String> = listOf<String>(),
    val suffix: List<String> = listOf<String>(),
    val use: String = ""
)