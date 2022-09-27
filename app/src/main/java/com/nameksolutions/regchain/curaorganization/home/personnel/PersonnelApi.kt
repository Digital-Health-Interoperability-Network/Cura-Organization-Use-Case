package com.nameksolutions.regchain.curaorganization.home.personnel


import com.nameksolutions.regchain.curaorganization.requests.CreatePractitionerRequest
import com.nameksolutions.regchain.curaorganization.responses.*
import com.nameksolutions.regchain.curaorganization.utils.Common.personnelRoute
import com.nameksolutions.regchain.curaorganization.utils.Common.practitionerRoleRoute
import com.nameksolutions.regchain.curaorganization.utils.Common.practitionerRoute
import retrofit2.http.*


interface PersonnelApi {
//

    @GET("$practitionerRoleRoute/roles")
    suspend fun getPractitionerRolesList(): PractitionerRolesResponse

    @POST(practitionerRoute)
    suspend fun createPractitioner(
        @Body createPractitioner: CreatePractitionerRequest
    ): CreatePractitionerResponse

    @GET(personnelRoute)
    suspend fun getAllPersonnelStats(): GetPersonnelStatsResponse
//
//   // @GET(practitionerRoute)
////    suspend fun getAllPractitioners(): AllPractitioner
//
//    @GET(practitionerRoute)
//    suspend fun getAllPractitioners(): FetchPractitionerResponse
////
////    @GET(practitionerRoute)
////    suspend fun getOnePractitioner(id: String):
//
//    @GET("$practitionerRoute/aggregates")
//    suspend fun getPractitionersByRole(@Query("code") code: String): FetchPractitionerResponse

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