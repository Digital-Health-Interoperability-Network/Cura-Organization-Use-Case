package com.nameksolutions.regchain.curaorganization.responses

import com.nameksolutions.regchain.curaorganization.requests.PractitionerRole
import com.nameksolutions.regchain.curaorganization.requests.Qualification

data class NewPractitioner(
    val __v: Int,
    val _id: String,
    val _personnel: String,
    val active: Boolean,
    val address: List<Addres>,
    val birthDate: String,
    val communication: List<String>,
    val gender: String,
    val id: String,
    val identifier: List<IdentifierLoginResponse>,
    val name: Name,
    val practitionerRole: List<PractitionerRole>,
    val qualification: List<Qualification>,
    val telecom: List<TelecomLoginResponse>
)