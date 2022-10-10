/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Namek Solutions Limited,
 * Abuja, FCT.
 * Nigeria
 */

package com.nameksolutions.regchain.curaorganization.responses.services

data class FetchHealthCareServices(
    val healthcareServices: List<HealthcareService>,
    val length: Int
)