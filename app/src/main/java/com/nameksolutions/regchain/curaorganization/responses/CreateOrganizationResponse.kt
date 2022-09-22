package com.nameksolutions.regchain.curaorganization.responses

data class CreateOrganizationResponse(
    val organization: Organization,
    val token: String
)