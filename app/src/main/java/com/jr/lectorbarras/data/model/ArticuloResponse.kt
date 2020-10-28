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



     /*val id_articulo : String ,
     val cod_articulo: String,
     val nombre: String,
     val precio_lista_1: String,
     val precio_lista_2: String,
     val precio_lista_3: String,
     val codbarras: String,
     val stock : String,
     val price_updated_at: String*/

)