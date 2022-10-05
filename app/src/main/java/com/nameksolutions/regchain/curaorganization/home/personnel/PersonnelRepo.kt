package com.nameksolutions.regchain.curaorganization.home.personnel

import com.nameksolutions.regchain.curaorganization.base.BaseRepo
import com.nameksolutions.regchain.curaorganization.requests.CreatePractitionerRequest
import com.nameksolutions.regchain.curaorganization.requests.PractitionerRoleRequest
import com.nameksolutions.regchain.curaorganization.utils.UserPreferences

class PersonnelRepo(
    private val api: PersonnelApi,
    private val prefs: UserPreferences
): BaseRepo() {

    suspend fun getAllPersonnelStats() = safeApiCall {
        api.getAllPersonnelStats()
    }
//
//    suspend fun getAllPractitioners() = safeApiCall {
//        api.getAllPractitioners()
//    }

    suspend fun getPractitionersByRole() = safeApiCall {
        api.getPractitionersByRole()
    }

    suspend fun getPractitionerRolesList() = safeApiCall {
        api.getPractitionerRolesList()
    }

    suspend fun createPractitioner(createPractitioner: CreatePractitionerRequest) = safeApiCall {
        api.createPractitioner(createPractitioner)
    }

    suspend fun createPractitionerRole(practitionerId: String,
                                       practitionerRoleRequest: PractitionerRoleRequest
    ) = safeApiCall { api.createPractitionerRole(practitionerId, practitionerRoleRequest) }

//    suspend fun getOnePractitioner(id: String) = safeApiCall {
//        api.getOnePractitioner(id)
//    }

//    suspend fun updatePractitioner(createPractitioner: PractitionerRequest) = safeApiCall {
//        api.updatePractitioner(createPractitioner)
//    }
}