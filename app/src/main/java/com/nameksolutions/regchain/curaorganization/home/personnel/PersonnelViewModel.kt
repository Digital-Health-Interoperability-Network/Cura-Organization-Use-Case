package com.nameksolutions.regchain.curaorganization.home.personnel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nameksolutions.regchain.curaorganization.base.BaseViewModel
import com.nameksolutions.regchain.curaorganization.network.Resource
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
////
//    //fetch all practitioner statistics
//    private val _practitionerStats: MutableLiveData<Resource<PersonnelStatsResponse>> = MutableLiveData()
//    val practitionerStats: LiveData<Resource<PersonnelStatsResponse>>
//    get() = _practitionerStats
//
//    //create Practitioner Observer Variables
//    private val _practitionerCreation: MutableLiveData<Resource<PractitionerCreationResponse>> =
//        MutableLiveData()
//    val practitionerCreationResponse: LiveData<Resource<PractitionerCreationResponse>>
//        get() = _practitionerCreation
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
//    //fetch all practitioner by role Observer Variables
//    private val _allPractitionerByRoleDetails: MutableLiveData<Resource<FetchPractitionerResponse>> =
//        MutableLiveData()
//    val allPractitionerByRoleDetails: LiveData<Resource<FetchPractitionerResponse>>
//        get() = _allPractitionerByRoleDetails
//
//    private val _practitionerRoleList: MutableLiveData<Resource<PractitionerRoleListResponse>> = MutableLiveData()
//    val practitionerRoleListResponse: LiveData<Resource<PractitionerRoleListResponse>>
//    get() = _practitionerRoleList
//
//
//
//
//
//
////    = = = = = = = = = = = = = = = FUNCTIONS TO FETCH FROM REPO  = = = = = = = = = = = = = = = = =
//
//
//    //function to fetch all practitioner stats
//    fun getAllPersonnelStats() = viewModelScope.launch {
//        _practitionerStats.value = Resource.Loading
//        _practitionerStats.value = repo.getAllPersonnelStats()
//    }
//
//    //function to create a new practitioner
//    fun createPractitioner(
//        createPractitionerRequest: PractitionerRequest
//    ) = viewModelScope.launch {
//        _practitionerCreation.value = Resource.Loading
//        _practitionerCreation.value =
//            repo.createPractitioner(createPractitionerRequest)
//    }
//
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
//    fun getPractitionersByRole(code: String) = viewModelScope.launch {
//        _allPractitionerByRoleDetails.value = Resource.Loading
//        _allPractitionerByRoleDetails.value = repo.getPractitionersByRole(code)
//    }
//
//    fun getPractitionerRolesList() = viewModelScope.launch {
//        _practitionerRoleList.value = Resource.Loading
//        _practitionerRoleList.value = repo.getPractitionerRolesList()
//    }


}