package com.nameksolutions.regchain.curaorganization.responses

data class OrganizationPatch(
    val _registryIdentifier: RegistryIdentifier,
    val alias: List<String>,
    val name: String
)