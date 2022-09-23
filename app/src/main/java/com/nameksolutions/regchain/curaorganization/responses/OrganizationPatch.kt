package com.nameksolutions.regchain.curaorganization.responses

data class OrganizationPatch(
    val _registryIdentifier: RegistryIdentifierX,
    val alias: List<String>,
    val name: String
)