package com.jr.lectorbarras.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jr.lectorbarras.R
import com.jr.lectorbarras.data.model.ApiInterface
import com.jr.lectorbarras.data.model.RetrofitClientApi
import com.jr.lectorbarras.data.model.SignInBody
import com.jr.lectorbarras.ui.MainActivity
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.AccessController.getContext


class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)


        var retrofitClient = RetrofitClientApi()
        val email = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.btnlogin)
        val loading = findViewById<ProgressBar>(R.id.loading)

        login.setOnClickListener {

            //Obtenemos usuario y contraseña
            val email = email.text.toString().trim()
            val password = password.text.toString().trim()
            val mensaje = ""
            val estado = ""

            val retIn = RetrofitClientApi.getRetrofitInstance().create(ApiInterface::class.java)
           // val signInInfo = SignInBody(email = email, password = password)
            //Log.i("tag", signInInfo.toString())
            retIn.login(estado, mensaje, email, password).enqueue(object : Callback<SignInBody> {
                override fun onResponse(
                    call: Call<SignInBody>,
                    response: Response<SignInBody>

                ) {
                    if (response.body()?.estado == "ok") {
                        Toast.makeText(this@LoginActivity, response.body()?.mensaje, Toast.LENGTH_SHORT).show()
                        val intent = Intent(applicationContext , MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@LoginActivity, response.body()?.mensaje, Toast.LENGTH_SHORT).show()
                    }

                }

                override fun onFailure(call: Call<SignInBody>, t: Throwable) {
                    Toast.makeText(
                        this@LoginActivity,
                        t.message,
                        Toast.LENGTH_SHORT
                    ).show()

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