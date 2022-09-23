package com.nameksolutions.regchain.curaorganization.requests

data class AvailableTime(
    val allDay: Boolean,
    val availableEndTime: String,
    val availableStartTime: String,
    val daysOfWeek: List<String>
)