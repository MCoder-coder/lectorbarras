package com.jr.lectorbarras.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jr.lectorbarras.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_modificar_stock.*

class ModificarStockActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modificar_stock)

        val intent = intent
        val stock = intent.getStringExtra("stock")

        tvCantidadUnidades.text = stock
      //  editText.setText(stock)
    }
}