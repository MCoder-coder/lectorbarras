package com.jr.lectorbarras.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.jr.lectorbarras.R
import com.jr.lectorbarras.data.model.ApiInterfaceRequest
import com.jr.lectorbarras.data.model.LoginResponse
import com.jr.lectorbarras.data.model.RetrofitClientApi
import com.jr.lectorbarras.ui.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)




        val email = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.btnlogin)
        val ipserver = findViewById<EditText>(R.id.editxtipservidor)

        login.setOnClickListener {

            //Obtenemos usuario y contraseña
            val email = email.text.toString().trim()
            val password = password.text.toString().trim()
            val mensaje = ""
            val estado = ""
            val data = HashMap<String, String>()
            val nombre = ""

            val retIn = RetrofitClientApi.getRetrofitInstance().create(ApiInterfaceRequest::class.java)
            retIn.login(email, password).enqueue(object :
                Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>

                ) {
                    if (response.body()?.estado == "ok") {
                        Log.i("tag", response.body().toString())
                        Toast.makeText(
                            this@LoginActivity,
                            response.body()?.mensaje,
                            Toast.LENGTH_SHORT
                        ).show()

                        val myNewHash = response.body()?.data?.get("hash");
                        // log (revisar)
                        Log.i("tag", "Hash: " + myNewHash);

                        // guardar hash en SharedPreference



                        val intent = Intent(applicationContext, MainActivity::class.java)
                        intent.putExtra("data", response.body()?.data)
                        intent.putExtra("hash", myNewHash)
                        startActivity(intent)


                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            response.body()?.mensaje,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(
                        this@LoginActivity,
                        t.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    FirebaseCrashlytics.getInstance().recordException(t!!)

                }
            })


        }

        /*override fun onStart() {
        super.onStart()
        Log.i("tag", SharedPrefManager.getInstance(this).isLoggedIn.toString() )
        if(SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }
    }
*/


    }
}