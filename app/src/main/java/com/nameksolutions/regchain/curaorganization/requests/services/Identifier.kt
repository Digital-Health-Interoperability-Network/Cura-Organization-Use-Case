/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Namek Solutions Limited,
 * Abuja, FCT.
 * Nigeria
 */

package com.nameksolutions.regchain.curaorganization.requests.services

data class Identifier(
    val active: Boolean,
    val rank: Int,
    val system: String,
    val use: String,
    val value: String
)