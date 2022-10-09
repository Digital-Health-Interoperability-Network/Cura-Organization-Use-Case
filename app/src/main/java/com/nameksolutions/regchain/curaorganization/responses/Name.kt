/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Namek Solutions Limited,
 * Abuja, FCT.
 * Nigeria
 */

package com.nameksolutions.regchain.curaorganization.responses

data class Name(
    val _id: String,
    val family: String,
    val given: List<String>,
    val prefix: List<String>,
    val suffix: List<Any>,
    val use: String
)