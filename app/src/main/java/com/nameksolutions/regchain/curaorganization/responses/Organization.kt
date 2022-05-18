package com.nameksolutions.regchain.curaorganization.responses

data class Organization(
    val __v: Int,
    val _id: String,
    val active: Boolean,
    val address: List<Any>,
    val alias: List<Any>,
    val createdAt: String,
    val identifier: List<Identifier>,
    val name: String,
    val partOf: String,
    val telecom: List<Telecom>,
    val type: List<Type>,
    val updatedAt: String
)