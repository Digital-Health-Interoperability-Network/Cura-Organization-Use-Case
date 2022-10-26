package com.nameksolutions.regchain.curaorganization.home.personnel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nameksolutions.regchain.curaorganization.base.BaseViewModel
import com.nameksolutions.regchain.curaorganization.network.Resource
import com.nameksolutions.regchain.curaorganization.requests.CreatePractitionerRequest
import com.nameksolutions.regchain.curaorganization.requests.PractitionerRoleRequest
import com.nameksolutions.regchain.curaorganization.responses.*
import com.nameksolutions.regchain.curaorganization.utils.Common.TAG
import kotlinx.coroutines.launch

class PersonnelViewModel(
    private val repo: PersonnelRepo
) : BaseViewModel(repo) {

    //fetch all practitioner statistics
    private val _practitionerStats: MutableLiveData<Resource<GetPersonnelStatsResponse>> =
        MutableLiveData()
    val practitionerStats: LiveData<Resource<GetPersonnelStatsResponse>>
        get() = _practitionerStats

    //create Practitioner Observer Variables
    private val _practitionerCreation: MutableLiveData<Resource<CreatePractitionerResponse>> =
        MutableLiveData()
    val practitionerCreationResponse: LiveData<Resource<CreatePractitionerResponse>>
        get() = _practitionerCreation

    //create Practitioner Observer Variables
    private val _practitionerRoleCreation: MutableLiveData<Resource<PractitionerRoleCreateResponse>> =
        MutableLiveData()
    val practitionerRoleCreationResponse: LiveData<Resource<PractitionerRoleCreateResponse>>
        get() = _practitionerRoleCreation

    //fetch Practitioner Observer Variables
    private val _onePractitionerInfo: MutableLiveData<Resource<SinglePractitioner>> =
        MutableLiveData()
    val onePractitionerInfo: LiveData<Resource<SinglePractitioner>>
        get() = _onePractitionerInfo

    //update Practitioner Observer Variables
    private val _updateOnePractitionerInfo: MutableLiveData<Resource<SinglePractitioner>> =
        MutableLiveData()
    val updateOnePractitionerInfo: LiveData<Resource<SinglePractitioner>>
        get() = _updateOnePractitionerInfo


    //fetch all practitioner by role Observer Variables
    private val _allPractitionerByRoleDetails: MutableLiveData<Resource<GetPractitionersResponse>> =
        MutableLiveData()
    val allPractitionerByRoleDetails: LiveData<Resource<GetPractitionersResponse>>
        get() = _allPractitionerByRoleDetails

    private val _practitionerRoleList: MutableLiveData<Resource<PractitionerRolesGetResponse>> =
        MutableLiveData()
    val practitionerRoleListResponse: LiveData<Resource<PractitionerRolesGetResponse>>
        get() = _practitionerRoleList

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

    fun getOnePractitioner(
        practitionerId: String
    ) = viewModelScope.launch {
        _onePractitionerInfo.value = Resource.Loading
        _onePractitionerInfo.value =
            repo.getOnePractitioner(practitionerId)
    }

    fun updateOnePractitioner(
        practitionerId: String,
        updatePractitionerRequest: CreatePractitionerRequest
    ) = viewModelScope.launch {
        _updateOnePractitionerInfo.value = Resource.Loading
        _updateOnePractitionerInfo.value =
            repo.updateOnePractitioner(practitionerId, updatePractitionerRequest)
    }

    fun getPractitionersByRole() = viewModelScope.launch {
        _allPractitionerByRoleDetails.value = Resource.Loading
        _allPractitionerByRoleDetails.value = repo.getPractitionersByRole()
        Log.d(TAG, "getPractitionersByRole: ${_allPractitionerByRoleDetails.value.toString()}")
    }

}