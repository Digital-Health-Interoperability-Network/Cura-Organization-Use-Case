package com.nameksolutions.regchain.curaorganization.requests

import com.google.gson.annotations.SerializedName

data class CreateOrganizationRequest(
    @SerializedName("active")
    val active: Boolean? = true,
    @SerializedName("identifier")
    val identifier: MutableList<Identifiers>? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("password")
    val password: String? = null,
    @SerializedName("telecom")
    val telecom: List<Telecom>? = null,
    @SerializedName("type")
    val type: Type? = null,
    @SerializedName("address")
    val address: List<AddressRequest>? = null,
    @SerializedName("_registryIdentifier")
    val _registryIdentifier: _RegIdentifiers? = null
)