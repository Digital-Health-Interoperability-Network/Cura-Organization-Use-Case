package com.nameksolutions.regchain.curaorganization.requests

data class CreatePractitionerRequest(
    val address: List<Address>,
    val birthDate: String,
    val communication: List<String>,
    val gender: String,
    val identifier: List<Identifier>,
    val name: Name,
    val telecom: List<Telecom>
)