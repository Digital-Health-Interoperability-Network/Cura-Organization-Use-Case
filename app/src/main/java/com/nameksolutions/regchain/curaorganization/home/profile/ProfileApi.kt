package com.nameksolutions.regchain.curaorganization.home.profile

import com.nameksolutions.regchain.curaorganization.responses.profile.GetOrganizationResponse
import com.nameksolutions.regchain.curaorganization.utils.Common
import com.nameksolutions.regchain.curaorganization.utils.Common.organizationRoute
import retrofit2.http.GET

interface ProfileApi {

    @GET(organizationRoute)
    suspend fun getOrganizationInfo(): GetOrganizationResponse
}