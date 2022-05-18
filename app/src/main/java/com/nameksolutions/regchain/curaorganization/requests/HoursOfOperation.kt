package com.nameksolutions.regchain.curaorganization.requests

data class HoursOfOperation(
    val fri: String = "",
    val mon: String = "",
    val sat: String = "",
    val sun: String = "",
    val thur: String = "",
    val tue: String = "",
    val wed: String = ""
)