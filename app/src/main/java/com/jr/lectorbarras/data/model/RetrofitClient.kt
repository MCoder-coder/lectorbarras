package com.jr.lectorbarras.data.model

import android.util.Base64
import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Retrofit instance class
 */
class RetrofitClient {


        val BASE_URL = "http://www.nanod10.com.ar/stock-api/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            Log.i("original: " , original.method.toString())
            val requestBuilder = original.newBuilder()
               // .addHeader("Authorization", AUTH)
                .method(original.method, original.body)

            val request = requestBuilder.build()
            Log.i("request: " , request.toString())
            chain.proceed(request)

        }.build()

    val instance: Api by lazy{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        Log.i("instance:: " , BASE_URL)
        retrofit.create(Api::class.java)
    }

}