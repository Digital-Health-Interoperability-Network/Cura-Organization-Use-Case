package com.nameksolutions.regchain.curaorganization.responses

data class RegistryIdentifierResponse(
    val EndDate: String,
    val _id: String,
    val availableTime: List<AvailableTimeResponse>,
    val daysOfOperation: List<String>,
    val facilityCode: String,
    val facilityLevel: String,
    val facilityName: String,
    val licenseStatus: Boolean,
    val operationalStatus: Boolean,
    val ownership: String,
    val ownershipType: String,
    val registrationStatus: Boolean,
    val startDate: String,
    val stateUID: String
)