package com.nameksolutions.regchain.curaorganization.utils

import kotlin.properties.Delegates

object Common {

    lateinit var organizationName: String
    var practitionerRolesList = listOf("Doctor",
        "Nurse",
        "Pharmacist",
        "Lab Scientist")

    var regStepCount = 0
    const val userRoute = "users"
    const val organizationRoute = "organization"
    const val personnelRoute = "_personnel"
    const val practitionerRoute = "practitioner"
    const val practitionerRoleRoute = "practitionerrole"

    const val TAG = "EQUA"



}