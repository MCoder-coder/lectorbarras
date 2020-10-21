package com.jr.lectorbarras.data.model

import retrofit2.Call
import retrofit2.http.*


public interface  ApiService {

    @GET("articulos")
    fun getArticulo(): Call<List<ArticuloModel>>


}