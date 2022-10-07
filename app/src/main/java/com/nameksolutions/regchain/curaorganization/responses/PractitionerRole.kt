/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Namek Solutions Limited,
 * Abuja, FCT.
 * Nigeria
 */

package com.nameksolutions.regchain.curaorganization.responses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class PractitionerRole(
    val __v: Int = 0,
    val _id: String = "",
    val _personnel: String = "",
    val active: Boolean = true,
    val availableTime: @RawValue List<AvailableTime>? = null,
    val code: @RawValue List<String> = listOf<String>(),
    val identifier: @RawValue List<IdentifierResponse>? = null,
    val notAvailable: @RawValue List<NotAvailable>? = null,
    val organization: String = "",
    val practitioner: String = "",
    val specialty: @RawValue List<String> = listOf<String>()
) : Parcelable