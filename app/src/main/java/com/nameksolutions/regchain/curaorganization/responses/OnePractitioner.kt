/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Namek Solutions Limited,
 * Abuja, FCT.
 * Nigeria
 */

package com.nameksolutions.regchain.curaorganization.responses

data class OnePractitioner(
    val __v: Int,
    val _id: String,
    val _personnel: String,
    val address: List<Any>,
    val communication: List<String>,
    val gender: String,
    val id: String,
    val identifier: List<Identifier>,
    val name: NameResponse,
    val practitionerRoles: List<PractitionerRole>,
    val qualification: List<Any>,
    val telecom: List<TelecomResponse>
)