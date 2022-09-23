package com.nameksolutions.regchain.curaorganization.requests

data class AvailableTime(
    val allDay: Boolean? = null,
    val availableEndTime: String? = null,
    val availableStartTime: String? = null,
    val daysOfWeek: List<String>? = null
)