package com.jr.lectorbarras.data.model

import com.google.gson.annotations.SerializedName

data class LoginJson(

    @SerializedName("hash") val hash : String,
    @SerializedName("nombre") val nombre : String

)