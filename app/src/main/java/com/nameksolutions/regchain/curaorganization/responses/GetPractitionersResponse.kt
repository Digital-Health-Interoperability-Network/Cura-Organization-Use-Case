/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Namek Solutions Limited,
 * Abuja, FCT.
 * Nigeria
 */

package com.nameksolutions.regchain.curaorganization.responses

data class GetPractitionersResponse(
    val length: Int,
    val practitioners: List<PractitionerResponse>
)