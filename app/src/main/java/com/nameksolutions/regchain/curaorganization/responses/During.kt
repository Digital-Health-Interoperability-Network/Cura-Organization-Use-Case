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

@Parcelize
data class During(
    val endTime: String = "",
    val startTime: String = ""
) : Parcelable