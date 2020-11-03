package com.jr.lectorbarras.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.jr.lectorbarras.R
import com.jr.lectorbarras.data.model.ApiInterfaceRequest
import com.jr.lectorbarras.data.model.RetrofitClientApi
import com.jr.lectorbarras.data.model.SessionManager
import com.jr.lectorbarras.data.model.VerificarHashResponse
import kotlinx.android.synthetic.main.activity_resultado.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultadoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado)


        val intent = intent
        val id_articulo = intent.getIntExtra("id_articulo" , 0)
        val cod_articulo = intent.getStringExtra("cod_articulo")
        val nombre = intent.getStringExtra("nombre")
        val precio_lista_1 = intent.getDoubleExtra("precio_lista_1", 0.0)
        val precio_lista_2 = intent.getDoubleExtra("precio_lista_2", 0.0)
        val precio_lista_3 = intent.getDoubleExtra("precio_lista_3", 0.0)
        val codbarras = intent.getStringExtra("codbarras")
        val stock = intent.getDoubleExtra("stock", 0.0)
        val price_updated_at = intent.getStringExtra("price_updated_at")

        idArticulo.text = cod_articulo.toString()
        tvcodigobarras.text = codbarras
        tvstock.text = stock.toString()
        tvpreciolista1.text = precio_lista_1.toString()
        tvpreciolista2.text = precio_lista_2.toString()
        tvpreciolista3.text = precio_lista_3.toString()
        tvFecha.text = price_updated_at
        tvnombre.text= nombre

        btnModificarStock.setOnClickListener {
            val intent = Intent(this , ModificarStockActivity::class.java)
            intent.putExtra("id_articulo", id_articulo)
            intent.putExtra("stock", stock)
            intent.putExtra("cod_articulo" , cod_articulo)
            startActivity(intent)


        }



    }




}