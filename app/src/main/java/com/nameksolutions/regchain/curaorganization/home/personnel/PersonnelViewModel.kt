package com.nameksolutions.regchain.curaorganization.home.personnel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nameksolutions.regchain.curaorganization.base.BaseViewModel
import com.nameksolutions.regchain.curaorganization.network.Resource
import com.nameksolutions.regchain.curaorganization.requests.CreatePractitionerRequest
import com.nameksolutions.regchain.curaorganization.requests.PractitionerRoleRequest
import com.nameksolutions.regchain.curaorganization.responses.*
import kotlinx.coroutines.launch

class PersonnelViewModel(
    private val repo: PersonnelRepo
) : BaseViewModel(repo) {
//
//    //fetch all practitioner statistics
//    private val _practitionerStats: MutableLiveData<Resource<List<Personnel>>> = MutableLiveData()
//    val practitionerStats: LiveData<Resource<List<Personnel>>>
//    get() = _practitionerStats
////
//
    //fetch all practitioner statistics
    private val _practitionerStats: MutableLiveData<Resource<GetPersonnelStatsResponse>> = MutableLiveData()
    val practitionerStats: LiveData<Resource<GetPersonnelStatsResponse>>
    get() = _practitionerStats
//
    //create Practitioner Observer Variables
    private val _practitionerCreation: MutableLiveData<Resource<CreatePractitionerResponse>> =
        MutableLiveData()
    val practitionerCreationResponse: LiveData<Resource<CreatePractitionerResponse>>
        get() = _practitionerCreation
//
    //create Practitioner Observer Variables
    private val _practitionerRoleCreation: MutableLiveData<Resource<PractitionerRoleCreateResponse>> =
        MutableLiveData()
    val practitionerRoleCreationResponse: LiveData<Resource<PractitionerRoleCreateResponse>>
        get() = _practitionerRoleCreation
//
//    //create Practitioner Observer Variables
//    private val _practitionerDetailsUpdate: MutableLiveData<Resource<PractitionerCreationResponse>> =
//        MutableLiveData()
//    val practitionerDetailsUpdate: LiveData<Resource<PractitionerCreationResponse>>
//        get() = _practitionerDetailsUpdate
//
//    //fetch all practitioner Observer Variables
//    private val _allPractitionerDetails: MutableLiveData<Resource<FetchPractitionerResponse>> =
//        MutableLiveData()
//    val allPractitionerDetails: LiveData<Resource<FetchPractitionerResponse>>
//        get() = _allPractitionerDetails
//
//
    //fetch all practitioner by role Observer Variables
    private val _allPractitionerByRoleDetails: MutableLiveData<Resource<GetPractitionersResponse>> =
        MutableLiveData()
    val allPractitionerByRoleDetails: LiveData<Resource<GetPractitionersResponse>>
        get() = _allPractitionerByRoleDetails

    private val _practitionerRoleList: MutableLiveData<Resource<PractitionerRolesGetResponse>> = MutableLiveData()
    val practitionerRoleListResponse: LiveData<Resource<PractitionerRolesGetResponse>>
    get() = _practitionerRoleList
//
//
//
//
//
//
////    = = = = = = = = = = = = = = = FUNCTIONS TO FETCH FROM REPO  = = = = = = = = = = = = = = = = =
//

    //function to fetch all practitioner stats
    fun getAllPersonnelStats() = viewModelScope.launch {
        _practitionerStats.value = Resource.Loading
        _practitionerStats.value = repo.getAllPersonnelStats()
    }


    fun getPractitionerRolesList() = viewModelScope.launch {
        _practitionerRoleList.value = Resource.Loading
        _practitionerRoleList.value = repo.getPractitionerRolesList()
    }


    //function to create a new practitioner
    fun createPractitioner(
        createPractitionerRequest: CreatePractitionerRequest
    ) = viewModelScope.launch {
        _practitionerCreation.value = Resource.Loading
        _practitionerCreation.value =
            repo.createPractitioner(createPractitionerRequest)
    }

    //function to create a new practitioner role
    fun createPractitionerRole(
        practitionerId: String,
        practitionerRoleRequest: PractitionerRoleRequest
    ) = viewModelScope.launch {
        _practitionerRoleCreation.value = Resource.Loading
        _practitionerRoleCreation.value =
            repo.createPractitionerRole(practitionerId, practitionerRoleRequest)
    }

////    fun updatePractitioner(
////        addPractitionerDetails: PractitionerRequest
////    ) = viewModelScope.launch {
////        _practitionerDetailsUpdate.value = Resource.Loading
////        _practitionerDetailsUpdate.value =
////            repo.updatePractitioner(addPractitionerDetails)
////    }
//
//    fun getAllPractitioners() = viewModelScope.launch {
//        _allPractitionerDetails.value = Resource.Loading
//        _allPractitionerDetails.value =
//            repo.getAllPractitioners()
//    }
//
    fun getPractitionersByRole() = viewModelScope.launch {
        _allPractitionerByRoleDetails.value = Resource.Loading
        _allPractitionerByRoleDetails.value = repo.getPractitionersByRole()
    }
//

}