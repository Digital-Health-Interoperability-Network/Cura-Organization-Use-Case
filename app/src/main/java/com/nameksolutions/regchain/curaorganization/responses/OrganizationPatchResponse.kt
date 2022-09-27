package com.nameksolutions.regchain.curaorganization.responses

data class OrganizationPatchResponse(
    val _registryIdentifier: RegistryIdentifierResponse,
    val alias: List<String>,
    val name: String
)