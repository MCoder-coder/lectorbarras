package com.jr.lectorbarras.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.jr.lectorbarras.R
import kotlinx.android.synthetic.main.activity_resultado.*

class ResultadoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado)


        val intent = intent
        val articulo = intent.getStringExtra("articulo")
        val precio = intent.getStringExtra("precio")
        val stock = intent.getStringExtra("stock")
        val txtarticulo = findViewById<TextView>(R.id.idArticulo)
        val result = intent.getStringExtra("result")
        txtarticulo.text = result
       // tvprecio.text = precio
        //tvstock.text = stock

        btnModificarStock.setOnClickListener {
            val intent = Intent(this , ModificarStockActivity::class.java)
            intent.putExtra("stock" , stock)
            startActivity(intent)

        }
    }




}