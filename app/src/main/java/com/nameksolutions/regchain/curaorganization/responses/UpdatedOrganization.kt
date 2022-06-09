package com.nameksolutions.regchain.curaorganization.responses

//data class for the response received when the organization (user) updates their information
data class UpdatedOrganization(
    val __v: Int,
    val _id: String,
    val active: Boolean,
    val addresses: List<AddressUpdatedOrganization>,
    val alias: List<Any>,
    val createdAt: String,
    val identifier: List<IdentifierUpdatedOrganization>,
    val name: String,
    val partOf: String,
    val telecom: List<TelecomUpdatedOrganization>,
    val type: List<TypeUpdatedOrganization>,
    val updatedAt: String
)