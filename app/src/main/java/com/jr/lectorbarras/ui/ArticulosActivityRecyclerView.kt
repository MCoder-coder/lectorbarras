package com.jr.lectorbarras.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.jr.lectorbarras.R
import com.jr.lectorbarras.data.model.*
import com.jr.lectorbarras.ui.adapter.ArticuloAdapater
import com.jr.lectorbarras.ui.adapter.ArticuloListener
import kotlinx.android.synthetic.main.activity_articulos_recycler_view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ArticulosActivityRecyclerView : AppCompatActivity() , ArticuloListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articulos_recycler_view)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerview)
        var articulosData: List<ArticulosJson> = ArrayList()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = ArticuloAdapater(articulosData, this@ArticulosActivityRecyclerView)
        //var articulosList: List<ArticulosJson>
        val position : Int
        val code = intent.getStringExtra("code");
        val hash : String = "3df76a7a956c427db7c76ccc8f2bce7e"

        Log.i("tag", "onCreateList")
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

                    /*Log.i("VerificandoHash" , "paso por onResponse")
                    Log.i("VerificandoHash" , response.raw().toString())*/


                    if (response.body()?.estado == "ok") {
                        Log.i("VerificandoHash", "body: " + response.body().toString())


                        //finish()

                    } else {
                        pref_save.hash  = ""
                        Toast.makeText(
                            this@ArticulosActivityRecyclerView,
                            response.body()?.mensaje,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }

                override fun onFailure(call: Call<VerificarHashResponse>, t: Throwable) {
                    Log.i("VerificandoHash" , "paso por onFailure")
                    pref_save.hash  = ""
/*
                    Toast.makeText(
                        this@LoginActivity,
                        t.message,
                        Toast.LENGTH_SHORT
                    ).show()

                    FirebaseCrashlytics.getInstance().recordException(t)
*/

                }
            })
        }

        val retIn = RetrofitClientApi.getRetrofitInstance(this).create(ApiInterfaceRequest::class.java)
        retIn.articuloResponse(code!!, pref_save.hash).enqueue(object :
            Callback<ArticuloResponse> {
            override fun onResponse(
                call: Call<ArticuloResponse>,
                response: Response<ArticuloResponse>
            ) {

                if (response.isSuccessful) {
                    val listJson = response.body()?.data?.articulos
                    articulosData = listJson as List<ArticulosJson>
                    if (articulosData.isEmpty()) {
                        rlBase.visibility = View.INVISIBLE
                        setContentView(R.layout.activity_sinresultados)

                    } else if (!articulosData.isEmpty()) {
                        rlBase.visibility = View.INVISIBLE
                        recyclerView.adapter = ArticuloAdapater(
                            articulosData,
                            this@ArticulosActivityRecyclerView
                        )
                    }

                }


            }

            override fun onFailure(call: Call<ArticuloResponse>, t: Throwable?) {
                Log.i("tag", "onFailure")
                Log.i("tag", t.toString())
                FirebaseCrashlytics.getInstance().recordException(t!!)
                Toast.makeText(
                    this@ArticulosActivityRecyclerView,
                    t.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })



    }

    override fun onArticulosClicked(articulosJson: ArticulosJson, position: Int) {


        val intent = Intent(this, ResultadoActivity::class.java)
        intent.putExtra("id_articulo", articulosJson.id_articulo)
        intent.putExtra("cod_articulo", articulosJson.cod_articulo)
        intent.putExtra("nombre", articulosJson.nombre)
        intent.putExtra("precio_lista_1", articulosJson.precio_lista_1)
        intent.putExtra("precio_lista_2", articulosJson.precio_lista_2)
        intent.putExtra("precio_lista_3", articulosJson.precio_lista_3)
        intent.putExtra("codbarras", articulosJson.codbarras)
        intent.putExtra("stock", articulosJson.stock)
        intent.putExtra("price_updated_at", articulosJson.price_updated_at)

        startActivity(intent)


    }



}