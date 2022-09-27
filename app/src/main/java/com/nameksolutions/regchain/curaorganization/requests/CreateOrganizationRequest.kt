package com.nameksolutions.regchain.curaorganization.requests

data class CreateOrganizationRequest(
    val alias: List<String>? = null,
    val identifier: List<IdentifierRequest>? = null,
    val name: String? = null,
    val password: String? = null,
    val telecom: List<TelecomRequest>? = null,
    val _registryIdentifier: _RegIdentifiersRequest? = null
)