package com.nameksolutions.regchain.curaorganization.network

import android.util.Log
import com.nameksolutions.regchain.curaorganization.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {


    companion object {
        private const val BASE_URL = "http://192.168.8.190/api/v1/"
    }

    //generic function to create retrofit client
    fun <Api> buildApi(
        api: Class<Api>
        ,authToken: String? = null//"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYyNzNkYWI4MmIwNDRjMTAxNjE4MWJkZiIsImlhdCI6MTY1MTc1OTgwMywiZXhwIjoxNjU0MzUxODAzfQ.whtIAs8psiI1yoR6hDlz7_pfEH2IQCkE96k8ecWU-kY" //new parameter to contain the user auth token when making other api calls
    ): Api {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    //attaches a header containing the user auth token to every api call to enable authorization to data
                    .addInterceptor { chain ->
                        chain.proceed(chain.request().newBuilder().also {
                            it.addHeader("Authorization", "Bearer $authToken")//eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYyNzNkYWI4MmIwNDRjMTAxNjE4MWJkZiIsImlhdCI6MTY1MTc1OTgwMywiZXhwIjoxNjU0MzUxODAzfQ.whtIAs8psiI1yoR6hDlz7_pfEH2IQCkE96k8ecWU-kY")
//                            it.addHeader("Content-Type", "application/json")
                            Log.d("EQUA", "buildApi: ${authToken}")
                        }.build())
                    }
                    .also { client ->
                        if (BuildConfig.DEBUG) {
                            val logging = HttpLoggingInterceptor()
                            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                            client.addInterceptor(logging)
                        }
                    }.build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }


}