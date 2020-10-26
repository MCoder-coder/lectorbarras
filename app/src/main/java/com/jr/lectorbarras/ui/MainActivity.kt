package com.jr.lectorbarras.ui
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.jr.lectorbarras.R
import com.jr.lectorbarras.data.model.ApiInterface
import com.jr.lectorbarras.data.model.ArticuloResponse
import com.jr.lectorbarras.data.model.LoginResponse
import com.jr.lectorbarras.data.model.RetrofitClientApi

import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       // txtName = findViewById(R.id.name)
        //txtSiteName = findViewById(R.id.site_name)
        val retIn = RetrofitClientApi.getRetrofitInstance().create(ApiInterface::class.java)
        retIn.articuloResponse(hash,estado, mensaje, cod_articulo, id_articulo, nombre, precio_lista_1, precio_lista_2, precio_lista_3, codbarras, stock, price_updated_at ).enqueue(object : Callback<ArticuloResponse> {
            override fun onResponse(
                call: Call<ArticuloResponse>,
                response: Response<ArticuloResponse>

            ) {
                if (response.body()?.estado == "ok") {
                    Toast.makeText(this@MainActivity,response.body()?.mensaje, Toast.LENGTH_SHORT).show()
                    intent.putExtra("cod_Articulo" , cod_articulo)
                    intent.putExtra("id_articulo" , id_articulo)
                    intent.putExtra("nombre" , nombre)
                    intent.putExtra("precilo_lista_1" , precio_lista_1)
                    intent.putExtra("precilo_lista_2" , precio_lista_2)
                    intent.putExtra("precilo_lista_3" , precio_lista_3)
                    intent.putExtra("codbarras" , codbarras)
                    intent.putExtra("stock", stock)
                    intent.putExtra("price_updated_at", price_updated_at)

                } else {
                    Toast.makeText(this@MainActivity, response.body()?.mensaje, Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<ArticuloResponse>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    t.message,
                    Toast.LENGTH_SHORT
                ).show()

            }
        })
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
                   // val obj = JSONObject(result.contents)

                    val intent = Intent(this, ResultadoActivity::class.java)
                   // val intent = Intent(this, ResultadoActivity::class.java)
                    //intent.putExtra("stock" , obj.getString("stock"))
                    //intent.putExtra("precio" ,obj.getString("precio"))
                    //intent.putExtra("articulo" ,obj.getString("articulo"))
                    intent.putExtra("result" , result.contents)
                    startActivity(intent)

                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


}