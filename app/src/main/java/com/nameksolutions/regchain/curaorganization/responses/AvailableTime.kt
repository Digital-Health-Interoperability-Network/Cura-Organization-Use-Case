/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Namek Solutions Limited,
 * Abuja, FCT.
 * Nigeria
 */

package com.nameksolutions.regchain.curaorganization.responses

data class AvailableTime(
    val _id: String,
    val allDay: Boolean,
    val availableEndTime: String,
    val availableStartTime: String,
    val daysOfWeek: List<String>
)