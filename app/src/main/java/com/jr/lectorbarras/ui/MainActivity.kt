package com.jr.lectorbarras.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import com.jr.lectorbarras.R
import com.jr.lectorbarras.data.model.ApiInterfaceRequest
import com.jr.lectorbarras.data.model.ArticuloResponse
import com.jr.lectorbarras.data.model.RetrofitClientApi
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    internal var txtName: TextView? = null

    internal var txtSiteName: TextView? = null

    internal var btnScan: Button? = null
    internal var qrScanIntegrator: IntentIntegrator? = null

    private var articuloModel: ArticuloResponse? = null


    val id_articulo : String = ""
    val cod_articulo: String = ""
    val nombre: String = ""
    val precio_lista_1: String = ""
    val precio_lista_2: String = ""
    val precio_lista_3: String = ""
    val codbarras: String = ""
    val stock : String = ""
    val price_updated_at: String = ""
    val mensaje = ""
    val estado = ""
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
        val hash_param = intent.getSerializableExtra("hash") as String
       // val code = "7798311170019"

       // Log.i("data main" , data_param.toString())
        Log.i("data main" , hash_param.toString())


        btnScan = findViewById(R.id.btnScan)
        btnScan!!.setOnClickListener { performAction() }

        qrScanIntegrator = IntentIntegrator(this)
        qrScanIntegrator?.setOrientationLocked(false)

        btnbuscar.setOnClickListener {
            //editTextBuscar = findViewById(R.id.editTextBuscar)

           var code = editTextBuscar.text.toString()

            val intent = Intent(this,ArticulosActivityRecyclerView::class.java)
            intent.putExtra("code",code)
            startActivity(intent)

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
                    intent.putExtra("articulo", obj.getString("Stock"))
                    intent.putExtra("precio", obj.getString("precio"))
                    intent.putExtra("articulo", obj.getString("articulo"))
                    startActivity(intent)


                } catch (e: JSONException) {
                    e.printStackTrace()
                   // val obj = JSONObject(result.contents)

                    val intent = Intent(this, ResultadoActivity::class.java)
                   // val intent = Intent(this, ResultadoActivity::class.java)
                    //intent.putExtra("stock" , obj.getString("stock"))
                    //intent.putExtra("precio" ,obj.getString("precio"))
                    //intent.putExtra("articulo" ,obj.getString("articulo"))
                    intent.putExtra("result", result.contents)
                    startActivity(intent)

                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


}