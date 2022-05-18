package com.nameksolutions.regchain.curaorganization.responses

data class OrganizationX(
    val __v: Int,
    val _id: String,
    val _registryIdentifier: RegistryIdentifier,
    val active: Boolean,
    val address: List<AddresX>,
    val alias: List<Any>,
    val createdAt: String,
    val identifier: List<IdentifierXX>,
    val name: String,
    val partOf: String,
    val telecom: List<TelecomXX>,
    val type: List<Any>,
    val updatedAt: String
)