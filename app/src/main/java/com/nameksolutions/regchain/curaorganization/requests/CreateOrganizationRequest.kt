package com.nameksolutions.regchain.curaorganization.requests

data class CreateOrganizationRequest(
    val alias: List<String>,
    val identifier: List<Identifier>,
    val name: String,
    val password: String,
    val telecom: List<Telecom>
)