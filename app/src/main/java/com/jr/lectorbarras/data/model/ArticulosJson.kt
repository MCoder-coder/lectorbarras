package com.jr.lectorbarras.data.model

import com.google.gson.annotations.SerializedName

data class ArticulosJson (
    @SerializedName("id_articulo") val id_articulo : Int,
    @SerializedName("cod_articulo") val cod_articulo : String,
    @SerializedName("nombre") val nombre : String,
    @SerializedName("precio_lista_1") val precio_lista_1 : Double,
    @SerializedName("precio_lista_2") val precio_lista_2 : Double,
    @SerializedName("precio_lista_3") val precio_lista_3 : Double,
    @SerializedName("codbarras") val codbarras : String,
    @SerializedName("stock") val stock : Double,
    @SerializedName("price_updated_at") val price_updated_at : String
)