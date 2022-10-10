package com.nameksolutions.regchain.curaorganization.home.services

import com.nameksolutions.regchain.curaorganization.base.BaseRepo
import com.nameksolutions.regchain.curaorganization.requests.services.NewServicesRequest
import com.nameksolutions.regchain.curaorganization.utils.UserPreferences

class ServicesRepo(
    private val api: ServicesApi,
    private val prefs: UserPreferences
): BaseRepo() {
//
//    suspend fun createHealthCareService(newServiceRequest: NewServiceRequest) = safeApiCall {
//        api.createHealthCareService(newServiceRequest)
//    }

    suspend fun createServicesInfo(newServicesInfoRequest: NewServicesRequest) = safeApiCall {
        api.createServicesInfo(newServicesInfoRequest)
    }

    suspend fun fetchServicesInfo() = safeApiCall {
        api.fetchServicesInfo()
    }
}