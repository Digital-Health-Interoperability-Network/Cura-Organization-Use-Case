package com.nameksolutions.regchain.curaorganization.responses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Practitoner(
    val __v: Int,
    val _id: String,
    val _personnel: String,
    val active: Boolean,
    val address: List<AddresXX>,
    val birthDate: String,
    val communication: List<Any>,
    val gender: String,
    val id: String,
    val identifier: List<IdentifierX>,
    val name: NameXX,
    val practitionerRole: List<PractitionerRoleX>,
    val qualification: List<QualificationX>,
    val telecom: List<TelecomX>
): Parcelable