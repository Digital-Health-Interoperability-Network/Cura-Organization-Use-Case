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
data class AvailableTime(
    val _id: String = "",
    val allDay: Boolean = false,
    val availableEndTime: String = "",
    val availableStartTime: String = "",
    val daysOfWeek: @RawValue List<String> = listOf<String>()
) : Parcelable