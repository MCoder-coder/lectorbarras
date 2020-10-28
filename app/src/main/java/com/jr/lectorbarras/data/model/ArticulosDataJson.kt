package com.jr.lectorbarras.data.model

import com.google.gson.annotations.SerializedName

data class ArticulosDataJson (

    @SerializedName("articulos") val articulos : List<ArticulosJson>,
    @SerializedName("timestamp") val timestamp : Int
)