package com.nameksolutions.regchain.curaorganization.home.services

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nameksolutions.regchain.curaorganization.base.BaseViewModel
import com.nameksolutions.regchain.curaorganization.network.Resource
import com.nameksolutions.regchain.curaorganization.requests.services.NewHealthCareServiceRequest
import com.nameksolutions.regchain.curaorganization.requests.services.NewServicesRequest
import com.nameksolutions.regchain.curaorganization.responses.services.*
import kotlinx.coroutines.launch

class ServicesViewModel(
    private val repo: ServicesRepo
) : BaseViewModel(repo) {

    //create new services request observer variables
    private val _newServicesInfo: MutableLiveData<Resource<NewServicesResponse>> = MutableLiveData()
    val newServicesInfo: LiveData<Resource<NewServicesResponse>>
        get() = _newServicesInfo

    //fetch services info observer variables
    private val _fetchServicesInfo: MutableLiveData<Resource<FetchServicesInfoResponse>> =
        MutableLiveData()
    val fetchServicesInfo: LiveData<Resource<FetchServicesInfoResponse>>
        get() = _fetchServicesInfo

    //create Practitioner Observer Variables
    private val _healthcareServiceCreation: MutableLiveData<Resource<NewHealthCareServiceResponse>> =
        MutableLiveData()
    val healthcareServiceCreationResponse: LiveData<Resource<NewHealthCareServiceResponse>>
        get() = _healthcareServiceCreation

    //fetch healthcare services observer variables
    private val _fetchHealthCareServices: MutableLiveData<Resource<FetchHealthCareServices>> =
        MutableLiveData()
    val fetchHealthCareServices: LiveData<Resource<FetchHealthCareServices>>
        get() = _fetchHealthCareServices

    //fetch single healthcare service observer variables
    private val _fetchOneHealthCareService: MutableLiveData<Resource<SingleHealthcareService>> =
        MutableLiveData()
    val fetchOneHealthCareService: LiveData<Resource<SingleHealthcareService>>
        get() = _fetchOneHealthCareService


    //function to create a new healthcare service
    fun createHealthCareService(
        newServiceRequest: NewHealthCareServiceRequest
    ) = viewModelScope.launch {
        _healthcareServiceCreation.value = Resource.Loading
        _healthcareServiceCreation.value =
            repo.createHealthCareService(newServiceRequest)
    }

    //function to create new services info
    fun createServicesInfo(newServicesRequest: NewServicesRequest) = viewModelScope.launch {
        _newServicesInfo.value = Resource.Loading
        _newServicesInfo.value = repo.createServicesInfo(newServicesRequest)
    }

    //function to fetch services info
    fun fetchServicesInfo() = viewModelScope.launch {
        _fetchServicesInfo.value = Resource.Loading
        _fetchServicesInfo.value = repo.fetchServicesInfo()
    }

    //function to fetch healthcare services
    fun fetchHealthCareServices() = viewModelScope.launch {
        _fetchHealthCareServices.value = Resource.Loading
        _fetchHealthCareServices.value = repo.fetchHealthCareServices()
    }

    //function to fetch a single healthcare service
    fun fetchOneHealthcareService(id: String) = viewModelScope.launch {
        _fetchOneHealthCareService.value = Resource.Loading
        _fetchOneHealthCareService.value = repo.fetchOneHealthcareService(id)
    }

}