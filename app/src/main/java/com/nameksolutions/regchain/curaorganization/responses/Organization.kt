package com.nameksolutions.regchain.curaorganization.responses

data class Organization(
    val __v: Int,
    val _id: String,
    val _personnel: String,
    val active: Boolean,
    val alias: List<String>,
    val identifier: List<Identifier>,
    val name: String,
    val telecom: List<Telecom>
)