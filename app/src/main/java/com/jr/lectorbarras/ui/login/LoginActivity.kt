package com.jr.lectorbarras.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.jr.lectorbarras.R
import com.jr.lectorbarras.data.model.*
import com.jr.lectorbarras.ui.MainActivity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create


class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)


        var retrofitClient = RetrofitClientApi()
        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.btnlogin)
        val loading = findViewById<ProgressBar>(R.id.loading)

        login.setOnClickListener {

            //Obtenemos usuario y contraseña
            val email = username.text.toString().trim()
            val password = password.text.toString().trim()


            val retIn = RetrofitClientApi.getRetrofitInstance().create(ApiInterface::class.java)
           // val signInInfo = SignInBody(email = email, password = password)
            //Log.i("tag", signInInfo.toString())
            retIn.login(email, password).enqueue(object : Callback<SignInBody> {
                override fun onFailure(call: Call<SignInBody>, t: Throwable) {
                    Toast.makeText(
                        this@LoginActivity,
                        t.message,
                        Toast.LENGTH_SHORT
                    ).show()

                }

                override fun onResponse(
                    call: Call<SignInBody>,
                    response: Response<SignInBody>

                ) {
                    if (response.isSuccessful) {
                        Log.i("tag" , response.body().toString())
                        Log.i("tag" , response.errorBody().toString())
                        Log.i("head" , response.headers().toString())

                            Toast.makeText(this@LoginActivity, "Login success!", Toast.LENGTH_SHORT)
                                .show()
                            val intent = Intent(applicationContext, MainActivity::class.java)
                            //intent.flags =
                            // Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)

                    } else {
                        Toast.makeText(this@LoginActivity, "Login failed!", Toast.LENGTH_SHORT)
                            .show()
                    }
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