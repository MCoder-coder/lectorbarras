package com.jr.lectorbarras.data.model

import retrofit2.http.*

// TIENE TODOS LOS REQUEST
interface ApiInterfaceRequest {


    @FormUrlEncoded
    //@Headers("Content-Type: application/json")
    @POST("login.php")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ):retrofit2.Call<LoginResponse>

    @FormUrlEncoded
    //@Headers("Content-Type: application/json")
    @POST("verificar-hash.php")
    fun verificarHash(
        @Field("hash") hash: String,

    ):retrofit2.Call<VerificarHashResponse>

    @FormUrlEncoded
    //@Headers("Content-Type: application/json")
    @POST("modificar-stock.php")
    fun modificarStock(
         @Field("hash") hash: String,
         @Field("id_articulo") id_articulo: String,
         @Field("cantidad_actual") cantidad_actual: String,
         @Field("cantidad_nueva") cantidad_Nueva: String
        ):retrofit2.Call<ModificarStockResponse>


    //@HTTP(method = "GET", path = "/login.php?", hasBody = true)
    //request de tipo get / ruta login.php url
    @FormUrlEncoded
    @POST("barcode.php")
    fun articuloResponse(
        @Field("codigo") codigo: String,
        @Field("hash") hash: String

        ): retrofit2.Call<ArticuloResponse>
}