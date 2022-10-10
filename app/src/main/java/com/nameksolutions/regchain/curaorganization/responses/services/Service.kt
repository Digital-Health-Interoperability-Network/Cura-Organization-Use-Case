/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Namek Solutions Limited,
 * Abuja, FCT.
 * Nigeria
 */

package com.nameksolutions.regchain.curaorganization.responses.services

data class Service(
    val __v: Int,
    val _id: String,
    val ambulanceServices: Boolean,
    val dentalServices: List<String>,
    val id: String,
    val inpatientServices: Boolean,
    val medicalServices: Boolean,
    val motuaryServices: Boolean,
    val numberOfBedsAvailable: Int,
    val obstericsAndGynecologyServices: List<String>,
    val onsiteLaboratory: Boolean,
    val organization: String,
    val outpatientService: Boolean,
    val pediatricsServices: List<String>,
    val specialClinicalServices: List<String>,
    val surgicalServices: List<String>,
    val totalNumberOfBeds: Int
)