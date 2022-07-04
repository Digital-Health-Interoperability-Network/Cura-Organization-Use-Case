package com.nameksolutions.regchain.curaorganization.auth

import com.google.gson.Gson
import com.nameksolutions.regchain.curaorganization.requests.CreateOrganizationRequest
import com.nameksolutions.regchain.curaorganization.requests.Identifiers
import com.nameksolutions.regchain.curaorganization.requests.Telecom
import com.nameksolutions.regchain.curaorganization.responses.*
import com.nameksolutions.regchain.curaorganization.utils.Common.organizationRoute
import com.nameksolutions.regchain.curaorganization.utils.Common.userRoute
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*
import java.util.*


interface AuthApi {

    val gson: Gson
        get() = Gson()

    @FormUrlEncoded
    @POST("$userRoute/signup")
    suspend fun signup(
        @Field("email") email: String
    ): SignUpResponse

    @FormUrlEncoded
    @POST("$userRoute/resendotp")
    suspend fun resendOtp(
        @Field("email") email: String
    ): SignUpResponse

    @FormUrlEncoded
    @POST("$userRoute/verifyotp")
    suspend fun verifyOtp(
        @Field("email") email: String,
        @Field("otp") otp: String
    ): OtpVerifyResponse


    //@FormUrlEncoded
    @Headers("Content-Type: application/json")
    @POST(organizationRoute)
    suspend fun createOrganization(
        @Body createOrganization: CreateOrganizationRequest
    ): OrganizationCreationResponse


    //@FormUrlEncoded
    @Headers("Content-Type: application/json")
    @PATCH(organizationRoute)
    suspend fun addOrganizationDetails( //to be used when user wants to replace the data in the db
        @Body addOrganizationDetail: CreateOrganizationRequest
//        @Field("name") name: String?,
//        @Field("alias") alias: MutableList<String>?,
//        @Field("password") password: String?,
//        @Field("identifier") identifier: MutableList<Identifiers>?,
////        @Field("telecom") telecom: MutableList<String>?,
//        @Body telecom: MutableList<Telecom>?,
//        @Field("active") active: Boolean?,
//        @Field("address") address: MutableList<String>?,
//        @Field("_registryIdentifier") _regIdentifiers: String?,
    ): OrganizationDetailsUpdateResponse

    @FormUrlEncoded
    @POST("$organizationRoute/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @PATCH(organizationRoute)
    suspend fun updateOrg(
        @Field("name") name: String,
        @Field("password") password: String,
        @Field("identifier") identifier: MutableList<Identifiers>,
        @Field("telecom") telecom: MutableList<Telecom>,
        @Field("active") active: Boolean
    ): OtpVerifyResponse

    @POST("$organizationRoute/logout")
    suspend fun logout(): ResponseBody


}