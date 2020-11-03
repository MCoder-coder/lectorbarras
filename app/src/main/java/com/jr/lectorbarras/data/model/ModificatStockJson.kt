package com.jr.lectorbarras.data.model

import com.google.gson.annotations.SerializedName

data class ModificatStockJson(


    @SerializedName("estado") val estado : String,
    @SerializedName("mensaje") val mensaje : String,
    @SerializedName("data") val data : List<String>

)