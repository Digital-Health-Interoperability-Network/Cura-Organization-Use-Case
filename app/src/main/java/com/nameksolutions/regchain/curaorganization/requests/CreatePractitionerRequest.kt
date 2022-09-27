package com.nameksolutions.regchain.curaorganization.requests

data class CreatePractitionerRequest(
    val address: List<AddressRequest> = listOf(),
    val birthDate: String? = null,
    val communication: List<String> = listOf(),
    val gender: String? = null,
    val identifier: List<IdentifierRequest> = listOf(),
    val name: NameRequest? = null,
    val telecom: List<TelecomRequest> = listOf(),
    val practitionerRoles: List<PractitionerRoleRequest> = listOf()
)