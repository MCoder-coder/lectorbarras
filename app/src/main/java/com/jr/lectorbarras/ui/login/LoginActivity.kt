package com.jr.lectorbarras.ui.login

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.jr.lectorbarras.R
import com.jr.lectorbarras.ui.MainActivity
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.btnlogin)
        val loading = findViewById<ProgressBar>(R.id.loading)

        login.setOnClickListener {

            //Obtenemos usuario y contraseña
            val  email = username.text.toString()
            val contrasena = password.text.toString()

            //Verificamos que los campos no este vacios
            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(contrasena)) {

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)


            } else {
                Toast.makeText(this, "Los campos no pueden esttar vacios", Toast.LENGTH_SHORT).show()
            }

        }

}


}