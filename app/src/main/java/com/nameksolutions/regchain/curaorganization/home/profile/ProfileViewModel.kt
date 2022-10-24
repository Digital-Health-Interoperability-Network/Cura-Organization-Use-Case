package com.nameksolutions.regchain.curaorganization.home.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nameksolutions.regchain.curaorganization.base.BaseViewModel
import com.nameksolutions.regchain.curaorganization.home.HomeRepo
import com.nameksolutions.regchain.curaorganization.network.Resource
import com.nameksolutions.regchain.curaorganization.requests.CreateOrganizationRequest
import com.nameksolutions.regchain.curaorganization.responses.OrganizationDetailsUpdateResponse
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

    //update organization details info observer variables
    private val _updateOrganizationDetails: MutableLiveData<Resource<OrganizationDetailsUpdateResponse>> =
        MutableLiveData()
    val updateOrganizationDetails: LiveData<Resource<OrganizationDetailsUpdateResponse>>
        get() = _updateOrganizationDetails



    fun getOrganizationInfo() = viewModelScope.launch {
        _fetchOrganizationInfo.value = Resource.Loading
        _fetchOrganizationInfo.value = repo.getOrganizationInfo()
    }

    fun updateOrganizationDetails (updateOrganizationDetails: CreateOrganizationRequest) = viewModelScope.launch {
        _updateOrganizationDetails.value = Resource.Loading
        _updateOrganizationDetails.value = repo.updateOrganizationDetails(updateOrganizationDetails)
    }


}