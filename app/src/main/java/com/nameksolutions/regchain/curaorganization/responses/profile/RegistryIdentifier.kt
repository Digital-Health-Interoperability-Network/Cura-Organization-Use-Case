/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Namek Solutions Limited,
 * Abuja, FCT.
 * Nigeria
 */

package com.nameksolutions.regchain.curaorganization.responses.profile

data class RegistryIdentifier(
    val _id: String,
    val availableTime: List<AvailableTime>,
    val daysOfOperation: List<Any>,
    val licenseStatus: Boolean,
    val operationalStatus: Boolean,
    val registrationStatus: Boolean
)