/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Namek Solutions Limited,
 * Abuja, FCT.
 * Nigeria
 */

package com.nameksolutions.regchain.curaorganization.requests.services

import com.google.gson.annotations.SerializedName

data class NewHealthCareServiceRequest(
    val active: Boolean,
    val appointmentRequired: Boolean,
    //val availabilityExceptions: String,
    val availableTime: List<AvailableTime>,
    val category: List<String>,
    @SerializedName("charactristics")
    val characteristics: List<Charactristic>,
    val comment: String,
    @SerializedName("communitcation")
    val communication: List<String>,
    val eligibility: List<Eligibility>,
    //val extraDetails: String,
    val name: String,
    val notAvailable: List<NotAvailable>? = listOf(),
    val program: List<Program>,
    val providedBy: String,
    val referralMethod: List<ReferralMethod>,
    val serviceProvisionCode: List<ServiceProvisionCode>,
    val speciality: Speciality,
    val telecom: List<Telecom>
)