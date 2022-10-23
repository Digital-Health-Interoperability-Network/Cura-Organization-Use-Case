/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Namek Solutions Limited,
 * Abuja, FCT.
 * Nigeria
 */

package com.nameksolutions.regchain.curaorganization.responses.profile

data class Addres(
    val _id: String,
    val city: String,
    val country: String,
    val district: String,
    val line: List<String>,
    val period: Period,
    val postalCode: String,
    val state: String,
    val text: String,
    val type: String,
    val use: String
)