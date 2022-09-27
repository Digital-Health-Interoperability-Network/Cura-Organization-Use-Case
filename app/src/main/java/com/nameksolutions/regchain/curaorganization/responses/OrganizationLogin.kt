package com.nameksolutions.regchain.curaorganization.responses

data class OrganizationLogin(
    val __v: Int,
    val _id: String,
    val _personnel: String,
    val _registryIdentifier: RegistryIdentifierResponse,
    val active: Boolean,
    val address: List<AddressResponse>,
    val alias: List<String>,
    val identifier: List<IdentifierResponse>,
    val name: String,
    val telecom: List<TelecomResponse>
)