package com.jr.lectorbarras.ui.login


import SessionManager
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.jr.lectorbarras.R
import com.jr.lectorbarras.data.model.ApiInterfaceRequest
import com.jr.lectorbarras.data.model.LoginResponse
import com.jr.lectorbarras.data.model.RetrofitClientApi
import com.jr.lectorbarras.data.model.VerificarHashResponse
import com.jr.lectorbarras.ui.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {


    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)





 /*       val crashButton = Button(this)
        crashButton.text = "Crash!"
        crashButton.setOnClickListener {
            throw RuntimeException("Test Crash") // Force a crash
        }

        addContentView(crashButton, ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT))
*/
        //guardo el hash en un hsared preferences
        // create shortcut if requested

        // create shortcut if requested


        setResult(RESULT_OK, intent)

        ivsiskitlogo.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.siskit.com/")
            )
            val chooseIntent = Intent.createChooser(intent, "Choose from below")
            startActivity(chooseIntent)
        }
        val pref_save = SessionManager.getInstance(this@LoginActivity)

        //verificar hash si el hash no esta vacio
        if (pref_save.hash != ""){
            //verificar request
            val verficarHash = RetrofitClientApi.getRetrofitInstance(this@LoginActivity).create(
                ApiInterfaceRequest::class.java
            )
            verficarHash.verificarHash(pref_save.hash).enqueue(object :
                Callback<VerificarHashResponse> {
                override fun onResponse(
                    call: Call<VerificarHashResponse>,
                    response: Response<VerificarHashResponse>

                ) {

                    /*Log.i("VerificandoHash" , "paso por onResponse")
                    Log.i("VerificandoHash" , response.raw().toString())*/


                    if (response.body()?.estado == "ok") {
                        Log.i("VerificandoHash", "body: " + response.body().toString())


                        //finish()

                    } else {
                        pref_save.hash = ""
                        Toast.makeText(
                            this@LoginActivity,
                            response.body()?.mensaje,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }

                override fun onFailure(call: Call<VerificarHashResponse>, t: Throwable) {
                    Log.i("VerificandoHash", "paso por onFailure")
                    pref_save.hash = ""

                    Toast.makeText(
                        this@LoginActivity,
                        t.message,
                        Toast.LENGTH_SHORT
                    ).show()

                    FirebaseCrashlytics.getInstance().recordException(t)


                }
            })

            val email = findViewById<EditText>(R.id.username)
            val password = findViewById<EditText>(R.id.password)
            val ipserver = findViewById<EditText>(R.id.editxtipservidor)


            val pref_save = SessionManager.getInstance(this)
            val isLoggedIn: Boolean = pref_save.equals("isLoggedIn")

            if (!isLoggedIn){
                val intent = Intent(applicationContext, MainActivity::class.java)
                val hash2  = pref_save.hash
                intent.putExtra("hash", hash2)
                startActivity(intent)


            }

            val emailIntent =  intent.getStringExtra("email")
            val hostIntent = intent.getStringExtra("host")
            email.setText(pref_save.email)
            ipserver.setText(pref_save.host)
            Log.i("tag" , pref_save.email)

            val hash = pref_save.hash




        }

        val email = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val ipserver = findViewById<EditText>(R.id.editxtipservidor)
        val login = findViewById<Button>(R.id.btnlogin)


        login.setOnClickListener {

            //Obtenemos usuario y contraseña
            val ipserver = ipserver.text.toString().trim()
            val email = email.text.toString().trim()
            val password = password.text.toString().trim()


            //guardar ip server en shared preferences

            pref_save.host = ipserver
            pref_save.email = email

            val retIn = RetrofitClientApi.getRetrofitInstance(this@LoginActivity).create(
                ApiInterfaceRequest::class.java
            )
            retIn.login(email, password).enqueue(object :
                Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>

                ) {

                    Log.i("onResponse", "paso por onResponse")
                    Log.i("Onresponse", response.raw().toString())


                    if (response.body()?.estado == "ok") {
                        Log.i("tag", response.body().toString())
                        Toast.makeText(
                            this@LoginActivity,
                            response.body()?.mensaje,
                            Toast.LENGTH_SHORT
                        ).show()

                        val myNewHash = response.body()!!.data.hash
                        // log (revisar)
                        Log.i("tag", "Hash: " + myNewHash);

                        // guardar hash en SharedPreference
                        pref_save.hash = myNewHash


                        val intent = Intent(applicationContext, MainActivity::class.java)
                        intent.putExtra("hash", myNewHash)
                        //intent.putExtra("hash", myNewHash)
                        startActivity(intent)
                        finish()


                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            response.body()?.mensaje,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.i("onFailure", "paso por onFailure")
                    Toast.makeText(
                        this@LoginActivity,
                        t.message,
                        Toast.LENGTH_SHORT
                    ).show()

                    FirebaseCrashlytics.getInstance().recordException(t)

                }
            })


        }


    }

    override fun onResume() {

        val email = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val ipserver = findViewById<EditText>(R.id.editxtipservidor)

        val pref_save = SessionManager.getInstance(this)
        email.setText(pref_save.email)
        ipserver.setText(pref_save.host )
        super.onResume()
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {

        val email = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val ipserver = findViewById<EditText>(R.id.editxtipservidor)
        val pref_save = SessionManager.getInstance(this)


        email.setText(pref_save.email)
        ipserver.setText(pref_save.host )
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onRestart() {

        val email = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val ipserver = findViewById<EditText>(R.id.editxtipservidor)
        val pref_save = SessionManager.getInstance(this)

        email.setText(pref_save.email)
        ipserver.setText(pref_save.host)
        super.onRestart()
    }

    override fun onDestroy() {
        val email = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val ipserver = findViewById<EditText>(R.id.editxtipservidor)
        val pref_save = SessionManager.getInstance(this)

        email.setText(pref_save.email)
        ipserver.setText(pref_save.host)

        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {

        val email = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val ipserver = findViewById<EditText>(R.id.editxtipservidor)
        val pref_save = SessionManager.getInstance(this)
        email.setText(pref_save.email)
        ipserver.setText(pref_save.host)
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun onPause() {

        val email = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val ipserver = findViewById<EditText>(R.id.editxtipservidor)
        val pref_save = SessionManager.getInstance(this)

        email.setText(pref_save.email)
        ipserver.setText(pref_save.host)
        super.onPause()
    }
}
