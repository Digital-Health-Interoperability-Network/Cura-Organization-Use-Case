package com.nameksolutions.regchain.curaorganization.requests

data class AvailableTime(
    val availableEndTime: String? = null,
    val availableStartTime: String? = null,
    val daysOfWeek: String? = null
)