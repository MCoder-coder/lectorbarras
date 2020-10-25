package com.jr.lectorbarras.data.model

import android.text.BoringLayout
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {



    @FormUrlEncoded
    @POST("login.php")
    fun login(
        @Field("estado") estado: String,
        @Field("mensaje") mensaje: String,
        @Field("email") email: String,
        @Field("password") password: String
    ):retrofit2.Call<SignInBody>

    @Headers("Content-Type:application/json")
    //@HTTP(method = "GET", path = "/login.php?", hasBody = true)
    //request de tipo get / ruta login.php url
   @POST("login.php")
    fun signin(
        @Body info: SignInBody,
        ): retrofit2.Call<ResponseBody>
}