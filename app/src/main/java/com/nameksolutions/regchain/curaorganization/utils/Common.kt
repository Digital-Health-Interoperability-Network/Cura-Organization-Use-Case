package com.nameksolutions.regchain.curaorganization.utils

import kotlin.properties.Delegates

object Common {

    lateinit var organizationName: String
    var practitionerRolesList = listOf(
        "Doctor",
        "Nurse",
        "Pharmacist",
        "Lab Scientist"
    )

    var regStepCount = 0
    const val userRoute = "users"
    const val organizationRoute = "organizations"
    const val organizationAddressRoute = "$organizationRoute/addresses"
    const val personnelRoute = "_personnels"
    const val practitionerRoute = "practitioners"
    const val practitionerRoleRoute = "practitioner-roles"


    const val healthcareServicesRoute = "healthcare-services"
    const val servicesRoute = "_services"



    const val TAG = "EQUA"

    val specialtiesMap = mapOf(
        "408467006" to "Adult mental illness",
        "394577000" to "Anesthetics",
        "394578005" to "Audiological medicine",
        "421661004" to "Blood banking and transfusion medicine",
        "408462000" to "Burns care",
        "394579002" to "Cardiology",
        "394580004" to "Clinical genetics",
        "394804000" to "Clinical cytogenetics and molecular genetics",
        "394803006" to "Clinical hematology",
        "408480009" to "Clinical immunology",
        "408454008" to "Clinical microbiology",
        "394809005" to "Clinical neuro-physiology",
        "394592004" to "Clinical oncology",
        "394600006" to "Clinical pharmacology",
        "394601005" to "Clinical physiology",
        "394581000" to "Community medicine",
        "408478003" to "Critical care medicine",
        "394812008" to "Dental medicine specialties",
        "408444009" to "Dental-General dental practice",
        "394582007" to "Dermatology",
        "408475000" to "Diabetic medicine",
        "410005002" to "Dive medicine",
        "394583002" to "Endocrinology",
        "419772000" to "Family practice",
        "394584008" to "Gastroenterology",
        "408443003" to "General medical practice",
        "394802001" to "General medicine",
        "394915009" to "General pathology",
        "394814009" to "General practice"
    )


}