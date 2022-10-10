package com.nameksolutions.regchain.curaorganization.home.services

import com.nameksolutions.regchain.curaorganization.requests.services.NewServicesRequest
import com.nameksolutions.regchain.curaorganization.responses.services.NewServicesResponse
import com.nameksolutions.regchain.curaorganization.utils.Common.healthcareServicesRoute
import com.nameksolutions.regchain.curaorganization.utils.Common.servicesRoute
import retrofit2.http.Body
import retrofit2.http.POST

interface ServicesApi {
//
//    @POST(healthcareServicesRoute)
//    suspend fun createHealthCareService(@Body newServiceRequest: NewServiceRequest): NewServiceCreationResponse

    @POST(servicesRoute)
    suspend fun createServicesInfo(@Body newServicesInfoRequest: NewServicesRequest): NewServicesResponse


}