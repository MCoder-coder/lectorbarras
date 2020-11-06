package com.jr.lectorbarras.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast



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

class ModificarStockActivity : AppCompatActivity(){

    lateinit var mySharedPref: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modificar_stock)


        setMySharedPref()

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




    /*    val recyclerView: RecyclerView = findViewById(R.id.recyclerview)
        var articulosData: List<ArticulosJson> = ArrayList()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = ArticuloAdapater(articulosData, this)*/
        //paso la cantidad nueva del stock
        val cantidadNueva = editTextUnidades.text
        tvCodigoarticulo.text = cod_articulo
        tvCantidadUnidades.text = cantidad_actual.toString()



        buttonGuardar.setOnClickListener {
            val retrofitModificartock = RetrofitClientApi.getRetrofitInstance(this).create(
                ApiInterfaceRequest::class.java)
            //request de modificar stock
            retrofitModificartock.modificarStock(mySharedPref.hash , id_articulo.toString(), cantidad_actual.toString() , cantidadNueva.toString(), ).enqueue(object :
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


    fun setMySharedPref() {

        this.mySharedPref = SessionManager.getInstance(this)
        //return this.mySharedPref;
    }



}