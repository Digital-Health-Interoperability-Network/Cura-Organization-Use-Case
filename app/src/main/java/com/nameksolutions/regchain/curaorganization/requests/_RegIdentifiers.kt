package com.nameksolutions.regchain.curaorganization.requests

data class _RegIdentifiers(
    val EndDate: String? = null,
    val daysOfOperation: MutableList<String> = mutableListOf(),
    val facilityCode: String? = null,
    val facilityLevel: String? = null,
    val facilityName: String? = null,
    val hoursOfOperation: HashMap<String, String> = hashMapOf(),
    val ownership: String? = null,
    val ownershipType: String? = null,
    val registrationNO: String? = null,
    val startDate: String? = null,
    val stateUID: String? = null
)