package com.example.fleetech.activities.ui.home

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.fleetech.R

class MainPODActivity : AppCompatActivity() {
    lateinit var bitmap: String
    lateinit var mBitmapValueFront: Bitmap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_podactivity)
        bitmap = intent.getStringExtra("bitmap").toString()
        println("nnnn$bitmap")
        val img_front = findViewById<ImageView>(R.id.img_frontImage)
        val img_back = findViewById<ImageView>(R.id.img_frontback)
        Glide.with(this).load(bitmap)
            .into(img_front)
        Glide.with(this).load(bitmap)
            .into(img_back)
    }
}