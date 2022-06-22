package com.nameksolutions.regchain.curaorganization.responses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Practitoner(
    val __v: Int,
    val _id: String,
    val _personnel: String,
    val active: Boolean?,
    val address: @RawValue List<AddresXX>?,
    val birthDate: String?,
    val communication: @RawValue List<Any>?,
    val gender: String?,
    val id: String,
    val identifier: @RawValue List<IdentifierX>?,
    val name: @RawValue NameXX?,
    val practitionerRole: @RawValue List<PractitionerRoleX>,
    val qualification: @RawValue List<QualificationX>?,
    val telecom: @RawValue List<TelecomX>?
): Parcelable