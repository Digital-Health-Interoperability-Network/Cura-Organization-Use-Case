package com.nameksolutions.regchain.curaorganization.responses

data class OtpVerifyResponse(
    val `data`: DataOTPVerifyMessage,
    val status: String
)