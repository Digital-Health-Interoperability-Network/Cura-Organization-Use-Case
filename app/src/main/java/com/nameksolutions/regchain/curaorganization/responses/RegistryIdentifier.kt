package com.nameksolutions.regchain.curaorganization.responses

data class RegistryIdentifier(
    val EndDate: Any,
    val _id: String,
    val daysOfOperation: List<String>,
    val facilityCode: String,
    val facilityLevel: String,
    val facilityName: String,
    val hoursOfOperation: HoursOfOperation,
    val licenseStatus: Boolean,
    val operationalStatus: Boolean,
    val ownership: String,
    val ownershipType: String,
    val registrationStatus: Boolean,
    val startDate: Any,
    val stateUID: String
)