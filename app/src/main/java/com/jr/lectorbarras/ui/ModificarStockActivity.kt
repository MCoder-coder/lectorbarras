package com.jr.lectorbarras.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.jr.lectorbarras.R
import com.jr.lectorbarras.data.model.*
import com.jr.lectorbarras.ui.adapter.ArticuloAdapater
import com.jr.lectorbarras.ui.adapter.ArticuloListener
import kotlinx.android.synthetic.main.activity_articulos_recycler_view.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_modificar_stock.*
import kotlinx.android.synthetic.main.activity_resultado.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModificarStockActivity : AppCompatActivity(), ArticuloListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modificar_stock)




        val cod_articulo = intent.getStringExtra("cod_articulo")

        val cantidad_actual = intent.getDoubleExtra("stock", 0.0)

        val id_articulo = intent.getIntExtra("id_articulo" , 0)
   /*     val nombre = intent.getStringExtra("nombre")
        val precio_lista_1 = intent.getDoubleExtra("precio_lista_1", 0.0)
        val precio_lista_2 = intent.getDoubleExtra("precio_lista_2", 0.0)
        val precio_lista_3 = intent.getDoubleExtra("precio_lista_3", 0.0)
        val codbarras = intent.getStringExtra("codbarras")
        val stock = intent.getDoubleExtra("stock", 0.0)
        val price_updated_at = intent.getStringExtra("price_updated_at")*/





        val cantidadNueva = editTextUnidades.text

        tvCodigoarticulo.text = cod_articulo
        tvCantidadUnidades.text = cantidad_actual.toString()

        val pref_save = SessionManager.getInstance(this)
        //verificar hash
        if (pref_save.hash != ""){
            //verificar request
            val verficarHash = RetrofitClientApi.getRetrofitInstance(this).create(ApiInterfaceRequest::class.java)
            verficarHash.verificarHash(pref_save.hash).enqueue(object :
                Callback<VerificarHashResponse> {
                override fun onResponse(
                    call: Call<VerificarHashResponse>,
                    response: Response<VerificarHashResponse>

                ) {

                    if (response.body()?.estado == "ok") {

                    } else {
                        pref_save.hash  = ""

                    }

                }

                override fun onFailure(call: Call<VerificarHashResponse>, t: Throwable) {
                    Log.i("VerificandoHash" , "paso por onFailure")
                    pref_save.hash  = ""

                    FirebaseCrashlytics.getInstance().recordException(t)

                }
            })
        }




        buttonGuardar.setOnClickListener {
            val retrofitModificartock = RetrofitClientApi.getRetrofitInstance(this).create(
                ApiInterfaceRequest::class.java)

            retrofitModificartock.modificarStock(pref_save.hash , id_articulo.toString(), cantidad_actual.toString() , cantidadNueva.toString(), ).enqueue(object :
                Callback<ModificarStockResponse> {
                override fun onResponse(
                    call: Call<ModificarStockResponse>,
                    response: Response<ModificarStockResponse>
                ) {


                    if (response.body()?.mensaje == "Stock Actualizado a: ${cantidadNueva}"){
                            Toast.makeText(
                                this@ModificarStockActivity,
                                response.body()?.mensaje,
                                Toast.LENGTH_SHORT
                            ).show()
                        /*val recyclerView: RecyclerView = findViewById(R.id.recyclerview)
                        var articulosData: List<ArticulosJson> = ArrayList()
                        recyclerView.layoutManager = LinearLayoutManager(this@ModificarStockActivity)
                        recyclerView.setHasFixedSize(true)
                        recyclerView.adapter = ArticuloAdapater(articulosData, this@ModificarStockActivity)*/



                    }else{
                        Toast.makeText(
                            this@ModificarStockActivity,
                            response.body()?.mensaje,
                            Toast.LENGTH_SHORT
                        ).show()
                    }








                }

                override fun onFailure(call: Call<ModificarStockResponse>, t: Throwable?) {
                    Log.i("tag", "onFailure")
                    Log.i("tag", t.toString())
                    FirebaseCrashlytics.getInstance().recordException(t!!)
                      Toast.makeText(
                          this@ModificarStockActivity,
                          t.message,
                          Toast.LENGTH_SHORT
                      ).show()
                }
            })


        }



    }

    override fun onArticulosClicked(articulosJson: ArticulosJson, position: Int) {
        TODO("Not yet implemented")
    }
}