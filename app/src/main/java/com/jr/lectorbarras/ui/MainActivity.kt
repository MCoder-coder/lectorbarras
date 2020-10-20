package com.jr.lectorbarras.ui
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.jr.lectorbarras.R

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_resultado.*
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    internal var txtName: TextView? = null

    internal var txtSiteName: TextView? = null

    internal var btnScan: Button? = null
    internal var qrScanIntegrator: IntentIntegrator? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       // txtName = findViewById(R.id.name)
        //txtSiteName = findViewById(R.id.site_name)

        btnScan = findViewById(R.id.btnScan)
        btnScan!!.setOnClickListener { performAction() }

        qrScanIntegrator = IntentIntegrator(this)
        qrScanIntegrator?.setOrientationLocked(false)

        btnbuscar.setOnClickListener {

        }


    }

    private fun performAction() {
        // Code to perform action when button is clicked.
        qrScanIntegrator?.initiateScan()

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

                    // Show values in UI.
                    //tvstock?.text = obj.getString("Stock")
                    //tvprecio?.text = obj.getString("precio")
                    //tvprecio?.text = obj.getString("articulo")

                    val intent = Intent(this, ResultadoActivity::class.java)
                    intent.putExtra("articulo" , obj.getString("Stock"))
                    intent.putExtra("precio" ,obj.getString("precio"))
                    intent.putExtra("articulo" ,obj.getString("articulo"))
                    startActivity(intent)


                } catch (e: JSONException) {
                    e.printStackTrace()
                    val obj = JSONObject(result.contents)

                    val intent = Intent(this, ResultadoActivity::class.java)
                   // val intent = Intent(this, ResultadoActivity::class.java)
                    intent.putExtra("stock" , obj.getString("stock"))
                    intent.putExtra("precio" ,obj.getString("precio"))
                    intent.putExtra("articulo" ,obj.getString("articulo"))
                    //intent.putExtra("articulo" , result.contents)
                    startActivity(intent)

                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


}