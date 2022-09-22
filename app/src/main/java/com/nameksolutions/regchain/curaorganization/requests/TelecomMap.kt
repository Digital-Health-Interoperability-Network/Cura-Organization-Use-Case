package com.nameksolutions.regchain.curaorganization.requests

data class TelecomMap(
    val system: String = "",
    val rank: String = "",
    val value: String = "",
    val use: String = "Official"
)
// {
//    object ModelMapper {
//        fun from(telecom: Telecom) =
//            TelecomMap(telecom.system, telecom.rank, telecom.value, telecom.use)
//    }
//}
