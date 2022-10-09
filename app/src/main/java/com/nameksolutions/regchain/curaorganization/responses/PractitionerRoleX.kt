/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Namek Solutions Limited,
 * Abuja, FCT.
 * Nigeria
 */

package com.nameksolutions.regchain.curaorganization.responses

data class PractitionerRoleX(
    val __v: Int,
    val _id: String,
    val _personnel: String,
    val active: Boolean,
    val availableTime: List<AvailableTimeX>,
    val code: List<String>,
    val identifier: List<Any>,
    val notAvailable: List<Any>,
    val organization: String,
    val practitioner: String,
    val specialty: List<String>
)