package com.nameksolutions.regchain.curaorganization.auth

import com.google.gson.Gson
import com.nameksolutions.regchain.curaorganization.requests.CreateOrganizationAddressRequest
import com.nameksolutions.regchain.curaorganization.requests.CreateOrganizationRequest
import com.nameksolutions.regchain.curaorganization.responses.*
import com.nameksolutions.regchain.curaorganization.utils.Common.organizationAddressRoute
import com.nameksolutions.regchain.curaorganization.utils.Common.organizationRoute
import com.nameksolutions.regchain.curaorganization.utils.Common.userRoute
import okhttp3.ResponseBody
import retrofit2.http.*
import java.util.*


interface AuthApi {

    val gson: Gson
        get() = Gson()

    //Initial Email Entry for OTP Sending
    @FormUrlEncoded
    @POST("$userRoute/signup")
    suspend fun signup(
        @Field("email") email: String
    ): SignUpResponse

    //Route to resend OTP
    @FormUrlEncoded
    @POST("$userRoute/resendotp")
    suspend fun resendOtp(
        @Field("email") email: String
    ): SignUpResponse

    //Route to verify the OTP
    @FormUrlEncoded
    @POST("$userRoute/verifyotp")
    suspend fun verifyOtp(
        @Field("email") email: String,
        @Field("otp") otp: String
    ): OtpVerifyResponse

    //route to create an organization
    //this route is used for creating the organization
    @Headers("Content-Type: application/json")
    @POST(organizationRoute)
    suspend fun createOrganization(
        @Body createOrganization: CreateOrganizationRequest
    ): CreateOrganizationResponse

    // TODO: GET Organization Function

    // TODO: PATCH Organization Function

    // TODO: DELETE Organization Function


    //route to create an organization address
    //this route is used for creating the organization address
    @Headers("Content-Type: application/json")
    @POST(organizationAddressRoute)
    suspend fun createOrganizationAddress(
        @Body createOrganization: CreateOrganizationAddressRequest
    ): OrganizationPatchInfoResponse


    // TODO: PATCH Organization Address Function

    // TODO: DELETE Organization Address Function

    //route to edit an organisation information
    //this route is also used for the completing part of creating the organization
    //@FormUrlEncoded
    @Headers("Content-Type: application/json")
    @PATCH(organizationRoute)
    suspend fun addOrganizationDetails( //to be used when user wants to replace the data in the db
        @Body addOrganizationDetail: CreateOrganizationRequest
    ): OrganizationDetailsUpdateResponse

    //route to login
    @FormUrlEncoded
    @POST("$organizationRoute/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

//    @FormUrlEncoded
//    @PATCH(organizationRoute)
//    suspend fun updateOrg(
//        @Field("name") name: String,
//        @Field("password") password: String,
//        @Field("identifier") identifier: MutableList<Identifiers>,
//        @Field("telecom") telecom: MutableList<Telecom>,
//        @Field("active") active: Boolean
//    ): OtpVerifyResponse

    //route to log out
    @POST("$organizationRoute/logout")
    suspend fun logout(): ResponseBody


}