package com.nameksolutions.regchain.curaorganization.auth

import com.nameksolutions.regchain.curaorganization.base.BaseRepo
import com.nameksolutions.regchain.curaorganization.requests.CreateOrganizationRequest
import com.nameksolutions.regchain.curaorganization.requests.Identifiers
import com.nameksolutions.regchain.curaorganization.requests.Telecom
import com.nameksolutions.regchain.curaorganization.utils.UserPreferences
import org.json.JSONObject
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

//    suspend fun createOrganization(
//        name: String,
//        alias: MutableList<String>,
//        password: String,
//        identifier: MutableList<Identifiers>,
//        telecom: MutableList<HashMap<String, String>>,
//        active: Boolean
//    ) = safeApiCall {
//        api.createOrganization(name, alias, password, identifier, telecom, active)
//    }
//
    suspend fun createOrganization(
    createOrganizationRequest: CreateOrganizationRequest
//        name: String,
//        alias: MutableList<String>,
//        password: String,
//        identifier: MutableList<Identifiers>,
//        telecom: MutableList<Telecom>?,
//        active: Boolean
    ) = safeApiCall {
        api.createOrganization(createOrganizationRequest)//, alias, password, identifier, telecom, active)
    }

    suspend fun addOrganizationDetails(
        addOrganizationDetails: CreateOrganizationRequest
//        name: String?,
//        alias: MutableList<String>?,
//        password: String?,
//        identifier: MutableList<Identifiers>?,
//        telecom: MutableList<Telecom>?,
//        active: Boolean?,
//        address: MutableList<String>?,
//        _regIdentifiers: String?,
    ) = safeApiCall {
        api.addOrganizationDetails(addOrganizationDetails)//name, alias, password, identifier, telecom, active, address, _regIdentifiers)
    }

    suspend fun login(
        email: String,
        password: String
    ) = safeApiCall { api.login(email, password) }

    suspend fun saveAuthToken(token: String){
        prefs.saveAuthToken(token)
    }

}