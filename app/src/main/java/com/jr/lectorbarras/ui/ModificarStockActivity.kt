package com.jr.lectorbarras.ui


import SessionManager
import SharedPrefManager
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.set
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.jr.lectorbarras.R
import com.jr.lectorbarras.data.model.ApiInterfaceRequest
import com.jr.lectorbarras.data.model.ModificarStockResponse
import com.jr.lectorbarras.data.model.RetrofitClientApi
import kotlinx.android.synthetic.main.activity_modificar_stock.*
import kotlinx.android.synthetic.main.activity_resultado.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ModificarStockActivity : AppCompatActivity(){

    lateinit var mySharedPref: SessionManager
    var mycodebar = ""
    lateinit var sharedPrefManager : SharedPrefManager
    var flagStockIsEdited = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modificar_stock)


        setMySharedPref()

        val cod_articulo = intent.getStringExtra("cod_articulo")

        val cantidad_actual = intent.getDoubleExtra("stock", 0.0)

        val id_articulo = intent.getIntExtra("id_articulo" , 0)

        val codbarras = intent.getStringExtra("codbarras")
        this.mycodebar = codbarras!!

        //paso la cantidad nueva del stock
        var cantidadNueva = editTextUnidades.text
        tvCodigoarticulo.text = cod_articulo
        tvCantidadUnidades.text = cantidad_actual.toString()



        val pref = SharedPrefManager.getInstance(this)





        buttonGuardar.setOnClickListener {
            val retrofitModificartock = RetrofitClientApi.getRetrofitInstance(this).create(
                ApiInterfaceRequest::class.java)
            //request de modificar stock
            retrofitModificartock.modificarStock(mySharedPref.hash , id_articulo.toString(), cantidad_actual.toString() , cantidadNueva.toString(), ).enqueue(object :
                Callback<ModificarStockResponse> {
                override fun onResponse(
                    call: Call<ModificarStockResponse>,
                    response: Response<ModificarStockResponse>
                ) {


                    if (response.body()?.mensaje == "Stock Actualizado a: $cantidadNueva"){

                        cantidadNueva.toString()

                        val cantidadActualizada = cantidadNueva

                        flagStockIsEdited = true



                        //pref.stock = cantidadActualizada.toString().toInt()
                        //tvCantidadUnidades.setText(cantidadNueva)
                        //Log.i("tag" , pref.stock.toString())

                        tvCantidadUnidades.setText(cantidadActualizada)

                        Log.i("tag cantidad nueva", cantidadActualizada.toString())

                            Toast.makeText(
                                this@ModificarStockActivity,
                                response.body()?.mensaje,
                                Toast.LENGTH_SHORT
                            ).show()


                        onBackPressed()

                    }else{
                        Toast.makeText(
                            this@ModificarStockActivity,
                            response.body()?.mensaje,
                            Toast.LENGTH_SHORT
                        ).show()
                    }



                }

                override fun onFailure(call: Call<ModificarStockResponse>, t: Throwable?) {
                    Log.i("tag", "onFailure")
                    Log.i("tag", t.toString())
                    FirebaseCrashlytics.getInstance().recordException(t!!)
                      Toast.makeText(
                          this@ModificarStockActivity,
                          t.message,
                          Toast.LENGTH_SHORT
                      ).show()
                }
            })


        }



    }

    fun setMySharedPref() {

        this.mySharedPref = SessionManager.getInstance(this)
        //return this.mySharedPref;
    }

    override fun onBackPressed() {

        val intent = Intent()
        intent.putExtra("codigobarras", this.mycodebar)
        intent.putExtra("flagStockIsEdited", flagStockIsEdited)

        Log.i("tag flag : ", flagStockIsEdited.toString())

        if(flagStockIsEdited) {
            setResult(Activity.RESULT_OK, intent)
        }else{
            setResult(Activity.RESULT_CANCELED, intent)
        }
        super.onBackPressed()
        finish()



    }

}