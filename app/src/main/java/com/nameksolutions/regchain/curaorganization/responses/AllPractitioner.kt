package com.nameksolutions.regchain.curaorganization.responses

import com.google.gson.annotations.SerializedName

data class AllPractitioner(
    @SerializedName("data")
    val practitionerList: DataAllPractitioner,
    val results: Int,
    val status: String
)