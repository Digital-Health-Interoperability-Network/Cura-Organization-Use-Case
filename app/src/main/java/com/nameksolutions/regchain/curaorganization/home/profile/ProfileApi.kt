package com.nameksolutions.regchain.curaorganization.home.profile

import com.nameksolutions.regchain.curaorganization.requests.CreateOrganizationRequest
import com.nameksolutions.regchain.curaorganization.requests.TelecomRequest
import com.nameksolutions.regchain.curaorganization.responses.OrganizationDetailsUpdateResponse
import com.nameksolutions.regchain.curaorganization.responses.profile.GetOrganizationResponse
import com.nameksolutions.regchain.curaorganization.responses.profile.UpdateTelecom
import com.nameksolutions.regchain.curaorganization.utils.Common
import com.nameksolutions.regchain.curaorganization.utils.Common.organizationRoute
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface ProfileApi {

    @GET(organizationRoute)
    suspend fun getOrganizationInfo(): GetOrganizationResponse

    @PATCH(organizationRoute)
    suspend fun updateOrganizationDetails(
        @Body updateOrganizationDetail: CreateOrganizationRequest
    ): OrganizationDetailsUpdateResponse

    @POST("$organizationRoute/telecoms")
    suspend fun updateOrganizationTelecom(@Body updateOrganizationTelecom: TelecomRequest): UpdateTelecom

}