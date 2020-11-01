package com.jr.lectorbarras.data.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(val estado: String, val mensaje: String,  val data: LoginDataJson)
//data class LoginResponse(val email: String, val password: String )

//val data: HashMap<String,String>?