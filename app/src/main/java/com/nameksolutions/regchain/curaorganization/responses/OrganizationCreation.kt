package com.nameksolutions.regchain.curaorganization.responses

data class OrganizationCreation(
    val __v: Int,
    val _id: String,
    val active: Boolean,
    val address: List<Any>,
    val alias: List<Any>,
    val createdAt: String,
    val identifierOrganizationCreation: List<IdentifierOrganizationCreation>,
    val name: String,
    val partOf: String,
    val telecomOrganizationCreation: List<TelecomOrganizationCreation>,
    val typeOrganizationCreation: List<TypeOrganizationCreation>,
    val updatedAt: String
)