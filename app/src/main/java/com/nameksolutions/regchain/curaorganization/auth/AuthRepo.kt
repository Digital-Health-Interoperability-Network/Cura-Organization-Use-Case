package com.nameksolutions.regchain.curaorganization.auth

import com.nameksolutions.regchain.curaorganization.base.BaseRepo
import com.nameksolutions.regchain.curaorganization.requests.CreateOrganizationAddressRequest
import com.nameksolutions.regchain.curaorganization.requests.CreateOrganizationRequest
import com.nameksolutions.regchain.curaorganization.utils.UserPreferences
import java.util.*

class AuthRepo(
    private val api: AuthApi,
    private val prefs: UserPreferences
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

    suspend fun createOrganization(
    createOrganizationRequest: CreateOrganizationRequest
//
    ) = safeApiCall {
        api.createOrganization(createOrganizationRequest)
    }

    suspend fun createOrganizationAddress(
        createOrganizationAddress: CreateOrganizationAddressRequest
    ) = safeApiCall {
        api.createOrganizationAddress(createOrganizationAddress)
    }

    suspend fun addOrganizationDetails(
        addOrganizationDetail: CreateOrganizationRequest
    ) = safeApiCall {
        api.addOrganizationDetails(addOrganizationDetail)
    }

    suspend fun login(
        email: String,
        password: String
    ) = safeApiCall { api.login(email, password) }

    suspend fun saveAuthToken(token: String){
        prefs.saveAuthToken(token)
    }

    suspend fun saveOrganisationName(organisationName: String){
        prefs.saveOrganisationName(organisationName)
    }

}