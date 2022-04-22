package com.nameksolutions.regchain.curaorganization.auth

import com.nameksolutions.regchain.curaorganization.base.BaseRepo

class AuthRepo(
    private val api: AuthApi
) : BaseRepo() {

    suspend fun generateOTP(email: String) = safeApiCall {
        api.signup(email)
    }
    suspend fun resendOTP(email: String) = safeApiCall {
        api.resendOtp(email)
    }
    suspend fun verifyOTP(email: String, otp: String) = safeApiCall {
        api.verifyOtp(email, otp)
    }

}