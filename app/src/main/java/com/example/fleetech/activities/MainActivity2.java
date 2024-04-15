package com.example.fleetech.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.fleetech.R;
import com.github.drjacky.imagepicker.ImagePicker;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        ImagePicker.with(this)
                .cameraOnly()	//User can only capture image using Camera
                .createIntent();
    }
}