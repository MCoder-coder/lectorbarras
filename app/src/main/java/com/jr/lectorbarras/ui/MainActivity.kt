package com.jr.lectorbarras.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import com.jr.lectorbarras.R
import com.jr.lectorbarras.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject


class MainActivity : AppCompatActivity() {


    internal var btnScan: Button? = null
    internal var qrScanIntegrator: IntentIntegrator? = null



    val stock : String = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnScan = findViewById(R.id.btnScan)
        btnScan!!.setOnClickListener { performAction() }

        qrScanIntegrator = IntentIntegrator(this)
        qrScanIntegrator?.setOrientationLocked(false)

        //btn buscar codigo de barras con por edittext
        btnbuscar.setOnClickListener {
            //editTextBuscar = findViewById(R.id.editTextBuscar)

            var code = editTextBuscar.text.toString()
            //paso a la activity recyclerview
            val intent = Intent(this, ArticulosActivityRecyclerView::class.java)
            intent.putExtra("code", code)
            startActivity(intent)


        }
        btnimagen.setOnClickListener { performAction() }


    }

    private fun performAction() {
        // Code to perform action when button is clicked.
        qrScanIntegrator?.initiateScan()
        //analizo con la camara el codigo de barras

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bnv_menu , menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.terminar_sesion){



            val pref_save = SessionManager.getInstance(this)
            //val email = pref_save.email
            //val host = pref_save.host
            pref_save.hash = ""

            val intent = Intent(this , LoginActivity::class.java)


            startActivity(intent)





            return true


        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            // If QRCode has no data.
            if (result.contents == null) {
                Toast.makeText(this, getString(R.string.result_not_found), Toast.LENGTH_LONG).show()
            } else {
                // If QRCode contains data.
                try {
                    // Converting the data to json format
                    val obj = JSONObject(result.contents)



                    val intent = Intent(this, ArticulosActivityRecyclerView::class.java)
                    intent.putExtra("code", obj.getString("code"))

                    startActivity(intent)


                } catch (e: JSONException) {
                    e.printStackTrace()
                   // val obj = JSONObject(result.contents)

                    val intent = Intent(this, ArticulosActivityRecyclerView::class.java)

                    intent.putExtra("code", result.contents)
                    startActivity(intent)

                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


}