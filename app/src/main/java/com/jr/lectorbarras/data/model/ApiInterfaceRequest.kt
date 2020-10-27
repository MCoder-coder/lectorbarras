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


    //@HTTP(method = "GET", path = "/login.php?", hasBody = true)
    //request de tipo get / ruta login.php url
    @FormUrlEncoded
    @POST("barcode.php")
    fun articuloResponse(
        @Field("codigo") codigo: String,
        @Field("hash") hash: String


         /*
         @Field("estado") estado: String,
         @Field("mensaje") mensaje: String,
         @FieldMap data: HashMap<String, String>,
         @Field("id_articulo") id_articulo: String,
         @Field("cod_articulo") cod_articulo: String,
         @Field("nombre") nombre: String,
         @Field("precio_lista_1") precio_lista_1: String,
         @Field("precio_lista_2") precio_lista_2: String,
         @Field("precio_lista_3") precio_lista_3: String,
         @Field("codbarras") codbarras: String,
         @Field("stock") stock: String,
         @Field("price_updated_at") price_updated_at: String,
         */

        ): retrofit2.Call<ArticuloResponse>
}