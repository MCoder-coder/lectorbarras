package com.jr.lectorbarras.ui

import SessionManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.zxing.integration.android.IntentIntegrator
import com.jr.lectorbarras.R
import com.jr.lectorbarras.data.model.*
import com.jr.lectorbarras.ui.adapter.ArticuloAdapater
import com.jr.lectorbarras.ui.adapter.ArticuloListener
import kotlinx.android.synthetic.main.activity_articulos_recycler_view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticulosActivityRecyclerView : AppCompatActivity() , ArticuloListener {

    lateinit var recyclerView: RecyclerView
    var articulosData: List<ArticulosJson> = ArrayList()
    lateinit var mySharedPref: SessionManager
    var code = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("skttag", "ejecutado ArticulosActivityRecyclerView@onCreate")

        setContentView(R.layout.activity_articulos_recycler_view)

        this.recyclerView = findViewById(R.id.recyclerview)
        this.code = intent.getStringExtra("code").toString();

        initRecyclerView(recyclerView)


        Log.i("skttag", "ArticulosActivityRecyclerView@onCreate - levantando shared preferences")
        setMySharedPref()

        // Hace el request a barcode.php para consultar el codigo ingresado/leido
        initRequestBarCode()



    }

    //inicia el recyclerview
    fun initRecyclerView(recyclerView: RecyclerView){
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = ArticuloAdapater(articulosData, this@ArticulosActivityRecyclerView)
    }


    fun initRequestBarCode(){
        // Request de
        val retIn = RetrofitClientApi.getRetrofitInstance(this).create(ApiInterfaceRequest::class.java)
        retIn.articuloResponse(code!!, mySharedPref.hash).enqueue(object :
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

        //paso lo datos a otro activity resultadoactivity
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

        //startActivity(intent)
        var requestCode = 10
        startActivityForResult(intent, requestCode)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10 ){
            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

            Log.i("onActivityResultDetalle", "paso por aca")

            initRecyclerView(recyclerView);
            initRequestBarCode()

        }
        Log.i("onActivityResultDetalle", "paso por aca fin")
    }





    /*
    * */
    fun setMySharedPref() {

        this.mySharedPref = SessionManager.getInstance(this)
        //return this.mySharedPref;
    }















}

