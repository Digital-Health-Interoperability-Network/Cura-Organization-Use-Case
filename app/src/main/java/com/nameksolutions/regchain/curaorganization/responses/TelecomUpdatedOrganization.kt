package com.nameksolutions.regchain.curaorganization.responses

data class TelecomUpdatedOrganization(
    val _id: String,
    val active: Boolean,
    val rank: Int,
    val system: String,
    val use: String,
    val value: String
)