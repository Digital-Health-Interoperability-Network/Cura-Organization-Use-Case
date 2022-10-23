/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Namek Solutions Limited,
 * Abuja, FCT.
 * Nigeria
 */

package com.nameksolutions.regchain.curaorganization.responses.profile

data class Telecom(
    val _id: String,
    val active: Boolean,
    val rank: Int,
    val system: String,
    val use: String,
    val value: String
)