package com.jr.lectorbarras.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ArticuloResponse(
     @SerializedName("estado")
     val estado: String,
     @SerializedName("mensaje")
     val mensaje: String,
     @SerializedName("data")
     val data: ArticulosDataJson,


)