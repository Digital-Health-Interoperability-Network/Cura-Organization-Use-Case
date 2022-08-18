package com.nameksolutions.regchain.curaorganization.requests

data class NewServiceRequest(
    val active: Boolean = true,
    val appointmentRequired: Boolean,
    val availabilityExceptions: String,
    val availableTime: List<AvailableTime>,
    val category: List<String>,
    val characteristics: List<Characteristic>,
    val comment: String,
    val communication: List<String>,
    val eligibility: List<Eligibility>,
    val extraDetails: String,
    val name: String,
    val notAvailable: List<NotAvailable>? = null,
    val photo: String,
    val program: List<Program>,
    val providedBy: String,
    val referralMethod: List<ReferralMethod>,
    val serviceProvisionCode: List<ServiceProvisionCode>,
    val speciality: Speciality,
    val telecom: List<Telecom>
)