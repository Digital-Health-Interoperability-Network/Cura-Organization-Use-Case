package com.nameksolutions.regchain.curaorganization.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nameksolutions.regchain.curaorganization.base.BaseViewModel
import com.nameksolutions.regchain.curaorganization.network.Resource
import com.nameksolutions.regchain.curaorganization.requests.CreateOrganizationRequest
import com.nameksolutions.regchain.curaorganization.requests.Identifiers
import com.nameksolutions.regchain.curaorganization.requests.Telecom
import com.nameksolutions.regchain.curaorganization.requests._RegIdentifiers
import com.nameksolutions.regchain.curaorganization.responses.*
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.*

class AuthViewModel(
    private val repo: AuthRepo
) : BaseViewModel(repo) {

    private val _otpResponse: MutableLiveData<Resource<SignUpResponse>> = MutableLiveData()
    val otpResponse: LiveData<Resource<SignUpResponse>>
        get() = _otpResponse

    private val _otpVerification: MutableLiveData<Resource<OtpVerifyResponse>> = MutableLiveData()
    val otpVerification: LiveData<Resource<OtpVerifyResponse>>
        get() = _otpVerification

    private val _organizationCreation: MutableLiveData<Resource<OrganizationCreationResponse>> =
        MutableLiveData()
    val organizationCreationResponse: LiveData<Resource<OrganizationCreationResponse>>
        get() = _organizationCreation

    private val _organizationDetailsUpdate: MutableLiveData<Resource<OrganizationDetailsUpdateResponse>> =
        MutableLiveData()
    val organizationDetailsUpdate: LiveData<Resource<OrganizationDetailsUpdateResponse>>
        get() = _organizationDetailsUpdate

    private val _loginResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse>>
        get() = _loginResponse

    fun generateOtp(email: String) = viewModelScope.launch {
        _otpResponse.value = Resource.Loading
        _otpResponse.value = repo.generateOTP(email)
    }

    fun resendOtp(email: String) = viewModelScope.launch {
        _otpResponse.value = Resource.Loading
        _otpResponse.value = repo.resendOTP(email)
    }

    fun verifyOtp(email: String, otp: String) = viewModelScope.launch {
        _otpVerification.value = Resource.Loading
        _otpVerification.value = repo.verifyOTP(email, otp)
    }

    fun createOrganization(
    createOrganizationRequest: CreateOrganizationRequest
    ) = viewModelScope.launch {
        _organizationCreation.value = Resource.Loading
        _organizationCreation.value =
            repo.createOrganization(createOrganizationRequest)//name, alias, password, identifier, telecom, active)
    }

    fun addOrganizationDetails(
        addOrganizationDetails: CreateOrganizationRequest
//        name: String? = "",
//        alias: MutableList<String>? = mutableListOf(),
//        password: String? = "",
//        identifier: MutableList<Identifiers>? = mutableListOf(),
//        telecom: MutableList<Telecom>? = mutableListOf(),
//        active: Boolean? = true,
//        address: MutableList<String>? = mutableListOf(),
//        _regIdentifiers: String? = "",
    ) = viewModelScope.launch {
        _organizationDetailsUpdate.value = Resource.Loading
        _organizationDetailsUpdate.value =
            repo.addOrganizationDetails(addOrganizationDetails)//name, alias, password, identifier, telecom, active, address, _regIdentifiers)
    }

    //this function will call the login api in the auth repository
    fun login(
        email: String,
        password: String,
    ) =
        viewModelScope.launch {
            _loginResponse.value = Resource.Loading
            _loginResponse.value = repo.login(email, password)
            Log.d("EQUA", "login: $email $password")
        }



    fun saveAuthToken(token: String) = viewModelScope.launch {
        repo.saveAuthToken(token)
    }
    fun saveOrganisationName(organisationName: String) = viewModelScope.launch {
        repo.saveAuthToken(organisationName)
    }
}