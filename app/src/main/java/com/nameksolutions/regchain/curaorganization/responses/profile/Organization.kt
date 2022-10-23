/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Namek Solutions Limited,
 * Abuja, FCT.
 * Nigeria
 */

package com.nameksolutions.regchain.curaorganization.responses.profile

data class Organization(
    val __v: Int,
    val _id: String,
    val _personnel: String,
    val _registryIdentifier: RegistryIdentifier,
    val _service: String,
    val active: Boolean,
    val address: List<Addres>,
    val alias: List<String>,
    val identifier: List<Identifier>,
    val name: String,
    val telecom: List<Telecom>
)