package com.nameksolutions.regchain.curaorganization.requests

data class _RegIdentifiersRequest(
    val EndDate: String,
    val availableTime: List<AvailableTime>,
    val daysOfOperation: List<String>,
    val facilityCode: String,
    val facilityLevel: String,
    val facilityName: String,
    val licenseStatus: Boolean,
    val operationalStatus: Boolean,
    val ownership: String,
    val ownershipType: String,
    val registrationNO: String,
    val registrationStatus: Boolean,
    val startDate: String,
    val stateUID: String
)