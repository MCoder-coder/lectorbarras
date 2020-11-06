package com.jr.lectorbarras.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.CaptureActivity
import com.jr.lectorbarras.R
import com.jr.lectorbarras.data.model.ArticuloResponse
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    internal var txtName: TextView? = null

    internal var txtSiteName: TextView? = null

    internal var btnScan: Button? = null
    internal var qrScanIntegrator: IntentIntegrator? = null

    private var articuloModel: ArticuloResponse? = null



    val stock : String = ""
    val hash : String = "3df76a7a956c427db7c76ccc8f2bce7e"
    //private lateinit var editTextBuscar: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // val data = HashMap<String, String>()
       // txtName = findViewById(R.id.name)
        //txtSiteName = findViewById(R.id.site_name)
        val intent = intent
        //val data_param = intent.getSerializableExtra("data") as HashMap<String, String>
//        val hash_param = intent.getSerializableExtra("hash") as String
       // val code = "7798311170019"

       // Log.i("data main" , data_param.toString())
       // Log.i("data main" , hash_param.toString())



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


    }

    private fun performAction() {
        // Code to perform action when button is clicked.
        qrScanIntegrator?.initiateScan()
        //analizo con la camara el codigo de barras

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

                    val intent = Intent(this, ArticulosActivityRecyclerView::class.java)
                    intent.putExtra("code", obj.getString("code"))

                    startActivity(intent)


                } catch (e: JSONException) {
                    e.printStackTrace()
                   // val obj = JSONObject(result.contents)

                    val intent = Intent(this, ArticulosActivityRecyclerView::class.java)
                   // val intent = Intent(this, ResultadoActivity::class.java)
                    //intent.putExtra("stock" , obj.getString("stock"))
                    //intent.putExtra("precio" ,obj.getString("precio"))
                    //intent.putExtra("articulo" ,obj.getString("articulo"))
                    intent.putExtra("code", result.contents)
                    startActivity(intent)

                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


}