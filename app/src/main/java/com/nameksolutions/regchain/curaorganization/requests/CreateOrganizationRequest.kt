package com.nameksolutions.regchain.curaorganization.requests

data class CreateOrganizationRequest(
    val alias: List<String>? = null,
    val identifier: List<Identifier>? = null,
    val name: String? = null,
    val password: String? = null,
    val telecom: List<Telecom>? = null,
    val _registryIdentifier: _RegIdentifiersRequest? = null
)