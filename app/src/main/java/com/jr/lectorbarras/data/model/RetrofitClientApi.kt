package com.jr.lectorbarras.data.model

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Retrofit instance class
 */
class RetrofitClientApi {



    companion object {
        val BASE_URL: String = "http://www.nanod10.com.ar/stock-api/"

        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY


        }


        val client: OkHttpClient = OkHttpClient.Builder().apply {

            this.addInterceptor(interceptor)

        }.build()
        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }


}