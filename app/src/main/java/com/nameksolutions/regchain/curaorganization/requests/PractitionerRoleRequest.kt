/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Namek Solutions Limited,
 * Abuja, FCT.
 * Nigeria
 */

package com.nameksolutions.regchain.curaorganization.requests

data class PractitionerRoleRequest(
    val active: Boolean = true,
    val availableTime: List<AvailableTimeRequest>? = null,
    val code: List<String>? = null,
    val notAvailable: List<NotAvailableRequest>? = null,
    val specialty: List<String>? = null
)