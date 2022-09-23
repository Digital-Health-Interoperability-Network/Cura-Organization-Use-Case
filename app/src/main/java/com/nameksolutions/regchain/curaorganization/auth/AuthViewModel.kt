package com.nameksolutions.regchain.curaorganization.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nameksolutions.regchain.curaorganization.base.BaseViewModel
import com.nameksolutions.regchain.curaorganization.network.Resource
import com.nameksolutions.regchain.curaorganization.requests.CreateOrganizationAddressRequest
import com.nameksolutions.regchain.curaorganization.requests.CreateOrganizationRequest
import com.nameksolutions.regchain.curaorganization.responses.*
import kotlinx.coroutines.launch
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

    private val _organizationCreation: MutableLiveData<Resource<CreateOrganizationResponse>> =
        MutableLiveData()
    val organizationCreationResponse: LiveData<Resource<CreateOrganizationResponse>>
        get() = _organizationCreation

    private val _organizationAddressCreation: MutableLiveData<Resource<OrganizationPatchInfoResponse>> =
        MutableLiveData()
    val organizationAddressCreation: LiveData<Resource<OrganizationPatchInfoResponse>>
        get() = _organizationAddressCreation

    private val _organizationDetailsUpdate: MutableLiveData<Resource<OrganizationDetailsUpdateResponse>> =
        MutableLiveData()
    val organizationDetailsUpdate: LiveData<Resource<OrganizationDetailsUpdateResponse>>
        get() = _organizationDetailsUpdate

    private val _loginResponse: MutableLiveData<Resource<OrganizationLoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<Resource<OrganizationLoginResponse>>
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
            repo.createOrganization(createOrganizationRequest)
    }

    fun createOrganizationAddress(
        createOrganizationAddress: CreateOrganizationAddressRequest
    ) = viewModelScope.launch {
        _organizationAddressCreation.value = Resource.Loading
        _organizationAddressCreation.value =
            repo.createOrganizationAddress(createOrganizationAddress)
    }

    fun addOrganizationDetails(
        addOrganizationDetails: CreateOrganizationRequest
    ) = viewModelScope.launch {
        _organizationDetailsUpdate.value = Resource.Loading
        _organizationDetailsUpdate.value =
            repo.addOrganizationDetails(addOrganizationDetails)
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
        repo.saveOrganisationName(organisationName)
    }
}