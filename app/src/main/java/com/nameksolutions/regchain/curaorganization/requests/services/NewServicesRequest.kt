/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Namek Solutions Limited,
 * Abuja, FCT.
 * Nigeria
 */

package com.nameksolutions.regchain.curaorganization.requests.services

data class NewServicesRequest(
    val ambulanceServices: Boolean = true,
    val dentalServices: List<String> = listOf(),
    val inpatientServices: Boolean = true,
    val medicalServices: Boolean = true,
    val motuaryServices: Boolean = true,
    val numberOfBedsAvailable: Int = 100,
    val obstericsAndGynecologyServices: List<String> = listOf(),
    val onsiteLaboratory: Boolean = true,
    val outpatientService: Boolean = true,
    val pediatricsServices: List<String> = listOf(),
    val specialClinicalServices: List<String> = listOf(),
    val status: Boolean = true,
    val surgicalServices: List<String> = listOf(),
    val totalNumberOfBeds: Int = 170
)