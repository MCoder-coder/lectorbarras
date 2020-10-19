package com.jr.lectorbarras
import android.Manifest;
import android.Manifest.permission.CAMERA
import android.Manifest.permission_group.CAMERA
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder.VideoSource.CAMERA
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.webkit.URLUtil
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.util.isEmpty
import com.google.zxing.integration.android.IntentIntegrator

import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    internal var txtName: TextView? = null

    internal var txtSiteName: TextView? = null

    internal var btnScan: Button? = null
    internal var qrScanIntegrator: IntentIntegrator? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtName = findViewById(R.id.name)
        txtSiteName = findViewById(R.id.site_name)

        btnScan = findViewById(R.id.btnScan)
        btnScan!!.setOnClickListener { performAction() }

        qrScanIntegrator = IntentIntegrator(this)
        qrScanIntegrator?.setOrientationLocked(false)


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
                    txtName?.text = obj.getString("name")
                    txtSiteName?.text = obj.getString("site_name")

                } catch (e: JSONException) {
                    e.printStackTrace()
                    txtName?.text = result.contents
                    // Data not in the expected format. So, whole object as toast message.
                    Toast.makeText(this, result.contents, Toast.LENGTH_LONG).show()
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


}