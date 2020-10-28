package com.jr.lectorbarras.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jr.lectorbarras.R
import com.jr.lectorbarras.data.model.*
import com.jr.lectorbarras.ui.adapter.ArticuloAdapater
import com.jr.lectorbarras.ui.adapter.ArticuloListener
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
        recyclerView.adapter = ArticuloAdapater(articulosData)
        //var articulosList: List<ArticulosJson>
        val position : Int
        val code = intent.getStringExtra("code");
        val hash : String = "3df76a7a956c427db7c76ccc8f2bce7e"

        Log.i("tag" , "onCreateList")

        val retIn = RetrofitClientApi.getRetrofitInstance().create(ApiInterfaceRequest::class.java)
        retIn.articuloResponse(code!!, hash).enqueue(object :
            Callback<ArticuloResponse> {
            override fun onResponse(
                call: Call<ArticuloResponse>,
                response: Response<ArticuloResponse>
            ) {

                Log.i("tag" ,response.body()?.estado.toString())
                Log.i("tag" ,response.body()?.data?.articulos!!.toString())
                //ArticulosData = response.body()?.data?.articulos!!
                //Log.i("tag" , ArticulosData.toString())

                if (response.isSuccessful) {
                    val xxx = response.body()?.data?.articulos
                    articulosData = xxx as List<ArticulosJson>
                    recyclerView.adapter = ArticuloAdapater(articulosData)

                }
                //recyclerView.adapter = ArticuloAdapater(ArticulosData)

            }

            override fun onFailure(call: Call<ArticuloResponse>, t: Throwable?) {
                Log.i("tag" , "onFailure")
                Log.i("tag" , t.toString())
            }
        })


      /*  retIn.articuloResponse(code!!, hash)
            .enqueue(object : Callback<ArticuloResponse> {
                override fun onResponse(
                    call: Call<ArticuloResponse>,
                    response: Response<ArticuloResponse>

                ) {
                    if (response.body()?.estado == "ok") {
                        Toast.makeText(
                            this@ArticulosActivityRecyclerView,
                            response.body()?.mensaje,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        val articulos_array = response.body()?.data?.get("articulos");
                        *//* if(articulos_array?.length){

                         }*//*


                        *//*  intent.putExtra("id_articulo", id_articulo)
                          intent.putExtra("nombre", nombre)
                          intent.putExtra("precilo_lista_1", precio_lista_1)
                          intent.putExtra("precilo_lista_2", precio_lista_2)
                          intent.putExtra("precilo_lista_3", precio_lista_3)
                          intent.putExtra("codbarras", codbarras)
                          intent.putExtra("stock", stock)
                          intent.putExtra("price_updated_at", price_updated_at)*//*

                    } else {
                        Toast.makeText(
                            this@ArticulosActivityRecyclerView,
                            response.body()?.mensaje,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }

                }

                override fun onFailure(call: Call<ArticuloResponse>, t: Throwable) {
                    Toast.makeText(
                        this@ArticulosActivityRecyclerView,
                        t.message,
                        Toast.LENGTH_SHORT
                    ).show()

                }
            })*/

    }

    override fun onArticulosClicked(articulosJson: ArticulosJson, position: Int) {
        Log.i("click" , articulosJson.cod_articulo)
    }

}