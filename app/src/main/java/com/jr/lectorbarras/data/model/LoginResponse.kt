package com.jr.lectorbarras.data.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("status_code")
    var statusCode: Int,

    @SerializedName("hash")
    var hash: String,

    @SerializedName("user")
    var user: UserModel
)