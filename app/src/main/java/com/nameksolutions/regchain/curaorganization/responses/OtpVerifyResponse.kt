package com.nameksolutions.regchain.curaorganization.responses

data class OtpVerifyResponse(
    val `data`: DataOTPVerifyMessageResponse,
    val status: String
)