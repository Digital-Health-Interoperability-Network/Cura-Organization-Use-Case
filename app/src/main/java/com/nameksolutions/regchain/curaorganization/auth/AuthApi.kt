package com.nameksolutions.regchain.curaorganization.auth

import com.nameksolutions.regchain.curaorganization.responses.Data
import com.nameksolutions.regchain.curaorganization.responses.OtpVerifyResponse
import com.nameksolutions.regchain.curaorganization.responses.SignUpResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {

    @FormUrlEncoded
    @POST("users/signup")
    suspend fun signup(
        @Field("email") email: String
    ): SignUpResponse

    @FormUrlEncoded
    @POST("users/resendotp")
    suspend fun resendOtp(
        @Field("email") email: String
    ): SignUpResponse

    @FormUrlEncoded
    @POST("users/verifyotp")
    suspend fun verifyOtp(
        @Field("email") email: String,
        @Field("otp") otp: String
    ): OtpVerifyResponse

}