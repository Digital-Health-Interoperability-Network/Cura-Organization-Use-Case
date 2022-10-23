package com.nameksolutions.regchain.curaorganization.home.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nameksolutions.regchain.curaorganization.base.BaseViewModel
import com.nameksolutions.regchain.curaorganization.home.HomeRepo
import com.nameksolutions.regchain.curaorganization.network.Resource
import com.nameksolutions.regchain.curaorganization.responses.profile.GetOrganizationResponse
import com.nameksolutions.regchain.curaorganization.responses.services.FetchServicesInfoResponse
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repo: ProfileRepo
) : BaseViewModel(repo)  {

    //fetch organization info observer variables
    private val _fetchOrganizationInfo: MutableLiveData<Resource<GetOrganizationResponse>> =
        MutableLiveData()
    val fetchOrganizationInfo: LiveData<Resource<GetOrganizationResponse>>
        get() = _fetchOrganizationInfo


    fun getOrganizationInfo() = viewModelScope.launch {
        _fetchOrganizationInfo.value = Resource.Loading
        _fetchOrganizationInfo.value = repo.getOrganizationInfo()
    }


}