package com.jr.lectorbarras.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.zxing.integration.android.IntentResult
import com.jr.lectorbarras.R
import com.jr.lectorbarras.data.model.ApiInterfaceRequest
import com.jr.lectorbarras.data.model.RetrofitClientApi
import com.jr.lectorbarras.data.model.SessionManager
import com.jr.lectorbarras.data.model.VerificarHashResponse
import kotlinx.android.synthetic.main.activity_resultado.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*

class ResultadoActivity : AppCompatActivity() {

    var mycodebar = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado)

        //obtengo lo datos del intent de articulos recyclerview
        val intent = intent
        val id_articulo = intent.getIntExtra("id_articulo", 0)
        val cod_articulo = intent.getStringExtra("cod_articulo")
        val nombre = intent.getStringExtra("nombre")
        val precio_lista_1 = intent.getDoubleExtra("precio_lista_1", 0.0)
        val precio_lista_2 = intent.getDoubleExtra("precio_lista_2", 0.0)
        val precio_lista_3 = intent.getDoubleExtra("precio_lista_3", 0.0)
        val codbarras = intent.getStringExtra("codbarras")
        this.mycodebar = codbarras!!
        val stock = intent.getDoubleExtra("stock", 0.0)
        val price_updated_at = intent.getStringExtra("price_updated_at")

        val nf = NumberFormat.getNumberInstance(Locale.getDefault())
        idArticulo.text = cod_articulo.toString()
        tvcodigobarras.text = codbarras
        tvstock.text = stock.toString()
        tvpreciolista1.text = "$" + nf.format( precio_lista_1)
        tvpreciolista2.text = "$" + nf.format( precio_lista_2)
        tvpreciolista3.text ="$" + nf.format( precio_lista_3)
        tvFecha.text = price_updated_at
        tvnombre.text= nombre




        btnModificarStock.setOnClickListener {
            //paso los datos al modificar stock activity
            val intent = Intent(this, ModificarStockActivity::class.java)
            intent.putExtra("id_articulo", id_articulo)
            intent.putExtra("cod_articulo", cod_articulo)
            intent.putExtra("stock", stock)
            intent.putExtra("nombre", nombre)
            intent.putExtra("precio_lista_1", precio_lista_1)
            intent.putExtra("precio_lista_2", precio_lista_2)
            intent.putExtra("precio_lista_3", precio_lista_3)
            intent.putExtra("codbarras", codbarras)
            intent.putExtra("stock", stock)
            intent.putExtra("price_updated_at", price_updated_at)
            startActivity(intent)


        }



    }


    override fun onBackPressed() {
        super.onBackPressed()


        val intent = Intent()
        intent.putExtra("codigobarras", this.mycodebar)
        setResult(Activity.RESULT_OK, intent)
        finish()


    }

}