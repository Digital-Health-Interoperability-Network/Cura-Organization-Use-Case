package com.nameksolutions.regchain.curaorganization.home.personnel


import com.nameksolutions.regchain.curaorganization.requests.CreatePractitionerRequest
import com.nameksolutions.regchain.curaorganization.requests.PractitionerRoleRequest
import com.nameksolutions.regchain.curaorganization.responses.*
import com.nameksolutions.regchain.curaorganization.utils.Common.personnelRoute
import com.nameksolutions.regchain.curaorganization.utils.Common.practitionerRoleRoute
import com.nameksolutions.regchain.curaorganization.utils.Common.practitionerRoute
import retrofit2.http.*


interface PersonnelApi {
//

    @GET("$practitionerRoleRoute/roles")
    suspend fun getPractitionerRolesList(): PractitionerRolesGetResponse

    @POST(practitionerRoute)
    suspend fun createPractitioner(
        @Body createPractitioner: CreatePractitionerRequest
    ): CreatePractitionerResponse

    @GET(personnelRoute)
    suspend fun getAllPersonnelStats(): GetPersonnelStatsResponse

    @POST("$practitionerRoute/{practitionerId}/$practitionerRoleRoute")
    suspend fun createPractitionerRole(@Path("practitionerId") practitionerId: String,
                                       @Body practitionerRoleRequest: PractitionerRoleRequest
    ): PractitionerRoleCreateResponse
//
//   // @GET(practitionerRoute)
////    suspend fun getAllPractitioners(): AllPractitioner
//
//    @GET(practitionerRoute)
//    suspend fun getAllPractitioners(): FetchPractitionerResponse
////
    @GET("$practitionerRoute/{practitionerId}")
    suspend fun getOnePractitioner(@Path("practitionerId") id: String): PractitionerResponse

    @GET("$practitionerRoute/")
    suspend fun getPractitionersByRole(): GetPractitionersResponse
//
//    @GET("$practitionerRoute/")
//    suspend fun getPractitionersByRole(@Query("code") code: String?): GetPractitionersResponse

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