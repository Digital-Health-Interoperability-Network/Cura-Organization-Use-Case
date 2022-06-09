package com.nameksolutions.regchain.curaorganization.responses

data class Name(
    val _id: String,
    val family: String,
    val given: List<String>,
    val prefix: List<String>,
    val text: String,
    val suffix: List<Any>
)