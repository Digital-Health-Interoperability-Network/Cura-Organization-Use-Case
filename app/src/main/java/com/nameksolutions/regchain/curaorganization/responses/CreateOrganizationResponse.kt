package com.nameksolutions.regchain.curaorganization.responses

data class CreateOrganizationResponse(
    val organization: OrganizationCreate,
    val token: String
)