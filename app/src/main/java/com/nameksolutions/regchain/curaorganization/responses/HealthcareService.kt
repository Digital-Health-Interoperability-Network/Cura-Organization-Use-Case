package com.nameksolutions.regchain.curaorganization.responses

data class HealthcareService(
    val __v: Int,
    val _id: String,
    val active: Boolean,
    val appointmentRequired: Boolean,
    val availabilityExceptions: String,
    val availableTime: List<AvailableTimeXXX>,
    val category: List<String>,
    val charactristics: List<Charactristic>,
    val communitcation: List<String>,
    val eligibility: List<Eligibility>,
    val extraDetails: String,
    val identifier: List<IdentifierXXX>,
    val name: String,
    val notAvailable: List<NotAvailableXX>,
    val photo: String,
    val program: List<Program>,
    val providedBy: String,
    val referralMethod: List<ReferralMethod>,
    val serviceProvisionCode: List<ServiceProvisionCode>,
    val speciality: List<Any>,
    val telecom: List<TelecomXX>,
    val type: List<Any>
)