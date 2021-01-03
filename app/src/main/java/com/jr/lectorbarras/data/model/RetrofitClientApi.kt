package com.jr.lectorbarras.data.model

import SessionManager
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.preference.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.prefs.Preferences


/**
 * Retrofit instance class
 */
class RetrofitClientApi {



    companion object {
/*
        lateinit var appContext: Context
        val prefs by lazy { KsPrefs(appContext) }
        //val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        val pref = PreferenceManager.getDefaultSharedPreferencesName(appContext);*/
        //var by lazy appContext: Context =



        lateinit var prefs : SessionManager

        var PROTOCOL = "http://"
        var HOST = ""
        var API_PREFIX = "/stock-api/"

        var BASE_URL: String = ""




        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }


        val client: OkHttpClient = OkHttpClient.Builder().apply {

            this.addInterceptor(interceptor)

        }.build()

        fun getRetrofitInstance(context: Context ): Retrofit {
            prefs = SessionManager.getInstance(context)
            HOST = prefs.host;

            BASE_URL = PROTOCOL + HOST + API_PREFIX

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }


}