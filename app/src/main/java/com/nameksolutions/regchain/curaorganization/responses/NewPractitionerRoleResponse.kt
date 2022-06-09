package com.nameksolutions.regchain.curaorganization.responses

data class NewPractitionerRoleResponse(
    val __v: Int,
    val _id: String,
    val availableTime: List<AvailableTime>,
    val code: String,
    val identifier: List<Any>,
    val notAvailable: List<Any>,
    val organization: String,
    val practitioner: String,
    val specialty: List<String>
)