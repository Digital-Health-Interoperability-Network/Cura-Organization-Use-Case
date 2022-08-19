package com.nameksolutions.regchain.curaorganization.home.services

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nameksolutions.regchain.curaorganization.base.BaseViewModel
import com.nameksolutions.regchain.curaorganization.home.HomeRepo
import com.nameksolutions.regchain.curaorganization.network.Resource
import com.nameksolutions.regchain.curaorganization.requests.NewServiceRequest
import com.nameksolutions.regchain.curaorganization.requests.PractitionerRequest
import com.nameksolutions.regchain.curaorganization.responses.NewServiceCreationResponse
import com.nameksolutions.regchain.curaorganization.responses.PractitionerCreationResponse
import kotlinx.coroutines.launch

class ServicesViewModel(
    private val repo: ServicesRepo
) : BaseViewModel(repo)  {


    //create Practitioner Observer Variables
    private val _healthcareServiceCreation: MutableLiveData<Resource<NewServiceCreationResponse>> =
        MutableLiveData()
    val healthcareServiceCreationResponse: LiveData<Resource<NewServiceCreationResponse>>
        get() = _healthcareServiceCreation



    //function to create a new practitioner
    fun createHealthCareService(
        newServiceRequest: NewServiceRequest
    ) = viewModelScope.launch {
        _healthcareServiceCreation.value = Resource.Loading
        _healthcareServiceCreation.value =
            repo.createHealthCareService(newServiceRequest)
    }

}