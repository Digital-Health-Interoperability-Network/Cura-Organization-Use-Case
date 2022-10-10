package com.nameksolutions.regchain.curaorganization.home.services

import com.nameksolutions.regchain.curaorganization.requests.services.NewHealthCareServiceRequest
import com.nameksolutions.regchain.curaorganization.requests.services.NewServicesRequest
import com.nameksolutions.regchain.curaorganization.responses.SinglePractitioner
import com.nameksolutions.regchain.curaorganization.responses.services.FetchHealthCareServices
import com.nameksolutions.regchain.curaorganization.responses.services.FetchServicesInfoResponse
import com.nameksolutions.regchain.curaorganization.responses.services.NewHealthCareServiceResponse
import com.nameksolutions.regchain.curaorganization.responses.services.NewServicesResponse
import com.nameksolutions.regchain.curaorganization.utils.Common
import com.nameksolutions.regchain.curaorganization.utils.Common.healthcareServicesRoute
import com.nameksolutions.regchain.curaorganization.utils.Common.servicesRoute
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ServicesApi {

    @POST(healthcareServicesRoute)
    suspend fun createHealthCareService(@Body newServiceRequest: NewHealthCareServiceRequest): NewHealthCareServiceResponse

    @GET(healthcareServicesRoute)
    suspend fun fetchHealthCareServices(): FetchHealthCareServices

    //create services offered info
    @POST(servicesRoute)
    suspend fun createServicesInfo(@Body newServicesInfoRequest: NewServicesRequest): NewServicesResponse

    //get services offered info
    @GET(servicesRoute)
    suspend fun fetchServicesInfo(): FetchServicesInfoResponse

    //get one health care service
    @GET("${healthcareServicesRoute}/{id}/")
    suspend fun getOneHealthcareService(@Path("id") id: String): SinglePractitioner


}