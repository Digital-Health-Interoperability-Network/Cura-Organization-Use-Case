/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Namek Solutions Limited,
 * Abuja, FCT.
 * Nigeria
 */

package com.nameksolutions.regchain.curaorganization.requests.services

data class AvailableTime(
    val allDay: Boolean? = true,
    val availableEndTime: String? = "",
    val availableStartTime: String? = "",
    val daysOfWeek: String? = ""
)