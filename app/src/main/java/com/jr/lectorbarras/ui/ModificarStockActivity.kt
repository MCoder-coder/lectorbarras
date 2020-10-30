package com.jr.lectorbarras.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jr.lectorbarras.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_modificar_stock.*
import kotlinx.android.synthetic.main.activity_resultado.*

class ModificarStockActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modificar_stock)

        val intent = intent
        val cod_articulo = intent.getStringExtra("cod_articulo")
        val stock = intent.getDoubleExtra("stock", 0.0)


        tvCodigoarticulo.text = cod_articulo
        tvCantidadUnidades.text = stock.toString()

    }
}