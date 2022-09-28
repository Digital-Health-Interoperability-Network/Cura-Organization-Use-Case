/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Namek Solutions Limited,
 * Abuja, FCT.
 * Nigeria
 */

package com.nameksolutions.regchain.curaorganization.responses

data class PractitionerRole(
    val __v: Int,
    val _id: String,
    val _personnel: String,
    val active: Boolean,
    val availableTime: List<AvailableTime>,
    val code: List<String>,
    val identifier: List<Any>,
    val notAvailable: List<NotAvailable>,
    val organization: String,
    val practitioner: String,
    val specialty: List<String>
)