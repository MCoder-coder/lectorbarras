package com.jr.lectorbarras.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ArticuloModel(

    @Expose
    @SerializedName("barcod")
    val barcode: Int,
    @Expose
    @SerializedName("articuloid")
    val articuloid: Int,
    @Expose
    @SerializedName("stock")
    val stock: Int,

)