package com.nameksolutions.regchain.curaorganization.responses

data class LoginResponse(
    val `data`: DataLoginResponse,
    val status: String,
    val token: String
)