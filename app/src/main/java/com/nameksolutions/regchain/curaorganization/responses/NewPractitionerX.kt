package com.nameksolutions.regchain.curaorganization.responses

data class NewPractitionerX(
    val __v: Int,
    val _id: String,
    val _personnel: String,
    val address: List<AddresX>,
    val birthDate: String,
    val communication: List<Any>,
    val gender: String,
    val id: String,
    val identifier: List<Any>,
    val name: NameX,
    val practitionerRole: List<PractitionerRole>,
    val qualification: List<Qualification>,
    val telecom: List<Telecom>
)