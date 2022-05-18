package com.nameksolutions.regchain.curaorganization.responses

import kotlinx.android.parcel.Parcelize
import java.io.Serializable

data class Telecom(
    val _id: String,
    val active: Boolean,
    val rank: Int,
    val system: String,
    val use: String,
    val value: String
)