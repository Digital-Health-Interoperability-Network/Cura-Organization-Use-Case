package com.nameksolutions.regchain.curaorganization.network

import android.util.Log
import com.nameksolutions.regchain.curaorganization.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {


    companion object {
        private const val BASE_URL = "https://192.168.8.190/api/v1/"
    }

    //generic function to create retrofit client
    fun <Api> buildApi(
        api: Class<Api>
        ,authToken: String? = null //new parameter to contain the user auth token when making other api calls
    ): Api {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    //attaches a header containing the user auth token to every api call to enable authorization to data
                    .addInterceptor { chain ->
                        chain.proceed(chain.request().newBuilder().also {
                            it.addHeader("Authorization", "Bearer $authToken")
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