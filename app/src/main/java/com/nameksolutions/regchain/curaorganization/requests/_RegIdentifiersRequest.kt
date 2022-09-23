package com.nameksolutions.regchain.curaorganization.requests

data class _RegIdentifiersRequest(
    val EndDate: String? = null,
    val availableTime: List<AvailableTime>? = null,
    val daysOfOperation: List<String>? = null,
    val facilityCode: String? = null,
    val facilityLevel: String? = null,
    val facilityName: String? = null,
    val licenseStatus: Boolean? = null,
    val operationalStatus: Boolean? = null,
    val ownership: String? = null,
    val ownershipType: String? = null,
    val registrationNO: String? = null,
    val registrationStatus: Boolean? = null,
    val startDate: String? = null,
    val stateUID: String? = null
)