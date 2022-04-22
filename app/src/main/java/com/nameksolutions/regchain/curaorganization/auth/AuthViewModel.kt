package com.nameksolutions.regchain.curaorganization.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nameksolutions.regchain.curaorganization.base.BaseViewModel
import com.nameksolutions.regchain.curaorganization.network.Resource
import com.nameksolutions.regchain.curaorganization.responses.Data
import com.nameksolutions.regchain.curaorganization.responses.OtpVerifyResponse
import com.nameksolutions.regchain.curaorganization.responses.SignUpResponse
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repo: AuthRepo
) : BaseViewModel(repo) {

    private val _otpResponse: MutableLiveData<Resource<SignUpResponse>> = MutableLiveData()
    val otpResponse: LiveData<Resource<SignUpResponse>>
    get() = _otpResponse

    private val _otpVerification: MutableLiveData<Resource<OtpVerifyResponse>> = MutableLiveData()
    val otpVerification: LiveData<Resource<OtpVerifyResponse>>
    get() = _otpVerification

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
}