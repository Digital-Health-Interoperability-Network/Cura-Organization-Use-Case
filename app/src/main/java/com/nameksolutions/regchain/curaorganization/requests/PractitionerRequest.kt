package com.nameksolutions.regchain.curaorganization.requests

data class PractitionerRequest(
    val active: Boolean?,
    val address: List<AddressRequest>?,
    val birthDate: String?,
    val communication: List<String>?,
    val gender: String?,
    val identifier: MutableList<Identifiers>?,
    val name: Name?,
    val qualification: List<Qualification>?,
    val telecom: List<Telecom>?,
    val practitionerRole: List<PractitionerRole>?
)