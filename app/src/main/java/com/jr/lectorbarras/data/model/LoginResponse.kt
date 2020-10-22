package com.jr.lectorbarras.data.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(val error: Boolean, val message:String, val user: UserModel)