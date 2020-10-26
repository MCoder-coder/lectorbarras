package com.jr.lectorbarras.data.model

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Retrofit instance class
 */
class RetrofitClientApi {


        val BASE_URL = "http://www.nanod10.com.ar/stock-api/"

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

  /* private val okHttpClient = OkHttpClient.Builder()
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

    val instance: ApiInterface by lazy{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        Log.i("instance:: " , BASE_URL)
        retrofit.create(ApiInterface::class.java)
    }*/

}