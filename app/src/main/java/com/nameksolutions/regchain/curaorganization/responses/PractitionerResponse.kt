package com.nameksolutions.regchain.curaorganization.responses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class PractitionerResponse(
    val __v: Int,
    val _id: String,
    val _personnel: String,
    val address: @RawValue List<AddressResponse>,
    val birthDate: String,
    val communication: List<String>,
    val gender: String,
    val id: String,
    val identifier: @RawValue List<IdentifierResponse>,
    val name: @RawValue NameResponse,
    val qualification: @RawValue List<Any>,
    val telecom: @RawValue List<TelecomResponse>,
    val practitionerRoles: @RawValue List<PractitionerRole>
): Parcelable