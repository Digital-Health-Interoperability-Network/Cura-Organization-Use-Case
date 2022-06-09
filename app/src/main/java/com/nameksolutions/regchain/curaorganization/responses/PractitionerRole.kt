package com.nameksolutions.regchain.curaorganization.responses

data class PractitionerRole(
    val __v: Int,
    val _id: String,
    val active: Boolean,
    val availableTime: List<AvailableTimeX>,
    val code: String,
    val identifier: List<Identifier>,
    val notAvailable: List<NotAvailable>,
    val organization: String,
    val practitioner: String,
    val specialty: List<String>
)