/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Namek Solutions Limited,
 * Abuja, FCT.
 * Nigeria
 */

package com.nameksolutions.regchain.curaorganization.responses.services

import com.google.gson.annotations.SerializedName

data class HealthcareService(
    val __v: Int,
    val _id: String,
    val _service: String,
    val active: Boolean,
    val appointmentRequired: Boolean,
    val availabilityExceptions: String,
    val availableTime: List<AvailableTime>,
    val category: List<String>,
    @SerializedName("charactristics")
    val characteristics: List<Charactristic>,
    val communitcation: List<String>,
    val eligibility: List<Eligibility>,
    val extraDetails: String,
    val identifier: List<Identifier>,
    val name: String,
    val notAvailable: List<NotAvailable>,
    val photo: String,
    val program: List<Program>,
    val providedBy: String,
    val referralMethod: List<ReferralMethod>,
    val serviceProvisionCode: List<ServiceProvisionCode>,
    val speciality: List<Any>,
    val telecom: List<Telecom>,
    val type: List<Any>
)