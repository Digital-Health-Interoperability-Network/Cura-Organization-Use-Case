package com.nameksolutions.regchain.curaorganization.requests

data class CreatePractitionerRequest(
    val address: List<AddressRequest>,
    val birthDate: String,
    val communication: List<String>,
    val gender: String,
    val identifier: List<IdentifierRequest>,
    val name: NameRequest,
    val telecom: List<TelecomRequest>
)