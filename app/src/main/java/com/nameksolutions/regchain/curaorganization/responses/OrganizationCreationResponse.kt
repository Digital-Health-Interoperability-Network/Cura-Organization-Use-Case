package com.nameksolutions.regchain.curaorganization.responses

data class OrganizationCreationResponse(
    val `data`: DataOrganizationCreation,
    val status: String,
    val token: String
)