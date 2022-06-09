package com.nameksolutions.regchain.curaorganization.responses

data class OrganizationLoginResponse(
    val __v: Int,
    val _id: String,
    val _registryIdentifier: RegistryIdentifier,
    val active: Boolean,
    val address: List<AddressLoginResponse>,
    val alias: List<Any>,
    val createdAt: String,
    val identifier: List<IdentifierLoginResponse>,
    val name: String,
    val partOf: String,
    val telecom: List<TelecomLoginResponse>,
    val type: List<Any>,
    val updatedAt: String
)