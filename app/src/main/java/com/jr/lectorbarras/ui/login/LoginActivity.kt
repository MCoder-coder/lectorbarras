package com.jr.lectorbarras.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.jr.lectorbarras.R
import com.jr.lectorbarras.data.model.RetrofitClient
import com.jr.lectorbarras.data.model.LoginResponse
import com.jr.lectorbarras.data.model.SharedPrefManager
import com.jr.lectorbarras.ui.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)



        var  retrofitClient = RetrofitClient()
        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.btnlogin)
        val loading = findViewById<ProgressBar>(R.id.loading)

        login.setOnClickListener {

            //Obtenemos usuario y contraseña
            val  email = username.text.toString().trim()
            val password = password.text.toString().trim()


/*
            if(email.isEmpty()){
                username.error = "Email required"
                password.requestFocus()
                return@setOnClickListener
            }


            if(password.isEmpty()){
                password.error = "Password required"
                password.requestFocus()
                return@setOnClickListener
            }*/

           // Log.i("interceptor",  retrofitClient.instance.login(email, password).toString())
            retrofitClient.instance
            retrofitClient.instance.login(email, password)
               .enqueue(object: Callback<LoginResponse>{
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                        Log.i("tag", t.message.toString())
                    }

                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        if(!response.body()?.error!!){
                            Log.i("tag", response.body().toString())
                            Toast.makeText(applicationContext, response.body().toString(), Toast.LENGTH_LONG).show()
                            //SharedPrefManager.getInstance(applicationContext).saveUser(response.body()?.user!!)

                            val intent = Intent(applicationContext, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                            startActivity(intent)


                        }else{
                            Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_LONG).show()
                        }

                    }
                })


        }

            //Verificamos que los campos no este vacios
         /*   if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(contrasena)) {
                apiClient.getApiService().login(LoginRequest(email = email, password = contrasena))
                Log.i("tag",apiClient.getApiService().login(LoginRequest(email = email, password = contrasena)).toString())
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)


            } else {
                Toast.makeText(this, "Los campos no pueden esttar vacios", Toast.LENGTH_SHORT).show()
            }*/

        }

    override fun onStart() {
        super.onStart()
        Log.i("tag", SharedPrefManager.getInstance(this).isLoggedIn.toString() )
        if(SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }
    }



}

