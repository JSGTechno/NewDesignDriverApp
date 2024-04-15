package com.example.fleetech.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.fleetech.R
import com.example.fleetech.util.PermissionClass
import com.example.fleetech.util.Session

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT = 5000L
    lateinit var sessionManager: Session


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_splash)
        PermissionClass.checkAndRequestPermissions(this);

        val img_front = findViewById<LinearLayout>(R.id.signature_img)
        sessionManager = Session(applicationContext)
        img_front.findViewById<LinearLayout>(R.id.signature_img).setOnClickListener {
            sessionManager.checkLogin()

        }
        if (ContextCompat.checkSelfPermission(this@SplashActivity,
                Manifest.permission.ACCESS_FINE_LOCATION) !==
            PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@SplashActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this@SplashActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            } else {
                ActivityCompat.requestPermissions(this@SplashActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            }
        }
        Handler().postDelayed(
            {
                sessionManager.checkLogin()
//                val i = Intent(this@SplashActivity, SecondImageActivity::class.java)
//                startActivity(i)
//                finish()
            }, SPLASH_TIME_OUT
        )

    }



override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                        grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    when (requestCode) {
        1 -> {
            if (grantResults.isNotEmpty() && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED) {
                if ((ContextCompat.checkSelfPermission(this@SplashActivity,
                        Manifest.permission.ACCESS_FINE_LOCATION) ===
                            PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                }
            } else {
                Log.d("hhh",""+"Permission Denied\"")
//                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
            return
        }
    }
}

}