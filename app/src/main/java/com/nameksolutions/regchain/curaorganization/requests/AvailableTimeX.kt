package com.nameksolutions.regchain.curaorganization.requests

data class AvailableTimeX(
    val availableEndTime: String,
    val availableStartTime: String,
    val daysOfWeek: List<String>
)