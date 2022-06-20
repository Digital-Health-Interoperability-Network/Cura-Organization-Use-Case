package com.nameksolutions.regchain.curaorganization.responses

data class PractitionerRoleX(
    val __v: Int,
    val _id: String,
    val active: Boolean,
    val availableTime: List<AvailableTimeXX>,
    val code: String,
    val identifier: List<IdentifierXX>,
    val notAvailable: List<NotAvailableX>,
    val organization: String,
    val practitioner: String,
    val specialty: List<String>
)