package com.nameksolutions.regchain.curaorganization.home.profile

import com.nameksolutions.regchain.curaorganization.base.BaseRepo
import com.nameksolutions.regchain.curaorganization.requests.CreateOrganizationRequest
import com.nameksolutions.regchain.curaorganization.utils.UserPreferences

class ProfileRepo(
    private val api: ProfileApi,
    private val prefs: UserPreferences
) : BaseRepo() {

    suspend fun getOrganizationInfo() = safeApiCall {
        api.getOrganizationInfo()
    }

    suspend fun updateOrganizationDetails(updateOrganizationDetails: CreateOrganizationRequest) =
        safeApiCall { api.updateOrganizationDetails(updateOrganizationDetails) }

}