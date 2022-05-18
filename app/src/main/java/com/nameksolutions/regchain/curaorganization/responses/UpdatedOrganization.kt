package com.nameksolutions.regchain.curaorganization.responses

data class UpdatedOrganization(
    val __v: Int,
    val _id: String,
    val active: Boolean,
    val address: List<Addres>,
    val alias: List<Any>,
    val createdAt: String,
    val identifier: List<IdentifierX>,
    val name: String,
    val partOf: String,
    val telecom: List<TelecomX>,
    val type: List<TypeX>,
    val updatedAt: String
)