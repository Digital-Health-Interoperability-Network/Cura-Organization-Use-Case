/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Namek Solutions Limited,
 * Abuja, FCT.
 * Nigeria
 */

package com.nameksolutions.regchain.curaorganization.requests.services

data class Telecom(
    val active: Boolean = true,
    val rank: Int? = 0,
    val system: String? = "",
    val use: String? = "",
    val value: String? = ""
)