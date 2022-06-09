package com.nameksolutions.regchain.curaorganization.responses

import com.google.gson.annotations.SerializedName

data class PractitionerRoleListResponse(
    @SerializedName("data")
    val practitionerRolesList: PractitionerRolesList,
    val status: String
)