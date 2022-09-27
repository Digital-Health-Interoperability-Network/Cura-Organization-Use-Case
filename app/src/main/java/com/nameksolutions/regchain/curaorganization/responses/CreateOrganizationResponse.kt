package com.nameksolutions.regchain.curaorganization.responses

data class CreateOrganizationResponse(
    val organization: OrganizationCreateResponse,
    val token: String
)