package com.nameksolutions.regchain.curaorganization.home.personnel


import com.nameksolutions.regchain.curaorganization.requests.CreateOrganizationRequest
import com.nameksolutions.regchain.curaorganization.requests.PractitionerRequest
import com.nameksolutions.regchain.curaorganization.responses.*
import com.nameksolutions.regchain.curaorganization.utils.Common
import com.nameksolutions.regchain.curaorganization.utils.Common.personnelRoute
import com.nameksolutions.regchain.curaorganization.utils.Common.practitionerRoleRoute
import com.nameksolutions.regchain.curaorganization.utils.Common.practitionerRoute
import retrofit2.http.*


interface PersonnelApi {


    @GET("$practitionerRoleRoute/roles")
    suspend fun getPractitionerRolesList(): PractitionerRoleListResponse

    @POST(practitionerRoute)
    suspend fun createPractitioner(@Body createPractitioner: PractitionerRequest): PractitionerCreationResponse

//    //@FormUrlEncoded
//    @Headers("Content-Type: application/json")
//    @POST(Common.organizationRoute)
//    suspend fun createOrganization(
//        @Body createOrganization: CreateOrganizationRequest
//    ): OrganizationCreationResponse


    @GET(personnelRoute)
    suspend fun getAllPersonnelStats(): PersonnelStatsResponse

    @GET(practitionerRoute)
    suspend fun getAllPractitioners(): AllPractitioner
//
//    @GET(practitionerRoute)
//    suspend fun getOnePractitioner(id: String):

    @GET(practitionerRoute)
    suspend fun getPractitionersByRole(@Query("code") code: String): List<NewPractitioner>

//    suspend fun getPractitionersByRole(code: String) = safeApiCall {
//        api.getPractitionersByRole(code)
//    }
//
//
//    suspend fun getOnePractitioner(id: String) = safeApiCall {
//        api.getOnePractitioner(id)
//    }
//
//    suspend fun updatePractitioner(createPractitioner: PractitionerRequest) = safeApiCall {
//        api.updatePractitioner(createPractitioner)
//    }
//
//    personnelRoute
//    practitionerRoute
//    practitionerRoleRoute
//


}