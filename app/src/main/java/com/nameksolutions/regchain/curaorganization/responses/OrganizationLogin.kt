package com.nameksolutions.regchain.curaorganization.responses

data class OrganizationLogin(
    val __v: Int,
    val _id: String,
    val _personnel: String,
    val _registryIdentifier: RegistryIdentifier,
    val active: Boolean,
    val address: List<Address>,
    val alias: List<String>,
    val identifier: List<Identifier>,
    val name: String,
    val telecom: List<Telecom>
)