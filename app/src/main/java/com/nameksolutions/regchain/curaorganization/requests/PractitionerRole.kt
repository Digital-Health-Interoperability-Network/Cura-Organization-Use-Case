package com.nameksolutions.regchain.curaorganization.requests

import java.util.HashMap

data class PractitionerRole(
    val availableTime: List<AvailableTime>? = null,
    val code: String? = null,
    val specialty: List<String>? = null
)