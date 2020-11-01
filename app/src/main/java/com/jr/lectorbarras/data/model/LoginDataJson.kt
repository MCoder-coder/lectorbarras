package com.jr.lectorbarras.data.model

import com.google.gson.annotations.SerializedName

data class LoginDataJson(

    @SerializedName("hash") val hash : String,
    @SerializedName("nombre") val nombre : String

)