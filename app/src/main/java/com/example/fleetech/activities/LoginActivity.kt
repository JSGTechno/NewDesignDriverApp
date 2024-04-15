package com.example.fleetech.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fleetech.databinding.ActivityLogin2Binding
import com.example.fleetech.retrofit.Apiuitils
import com.example.fleetech.retrofit.RetrofitClient
import com.example.fleetech.retrofit.model.UpdateModel
import com.example.fleetech.retrofit.response.VerifyNewRespone
import com.example.fleetech.util.Session
import com.example.fleetech.viewModel.LoginViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable
import java.util.*


class LoginActivity : AppCompatActivity() {
    lateinit var mBinding: ActivityLogin2Binding
    lateinit var viewModel: LoginViewModel
    lateinit var session: Session
    private var lastLocation: Location? = null
    lateinit var address: String
    private var fusedLocationClient: FusedLocationProviderClient? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLogin2Binding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        session = Session(applicationContext)
        setContentView(mBinding.root)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getCurrentLocation();
        mBinding.btnOTP.setOnClickListener {
            if (mBinding.editTextNumberPassword.length() < 4) {
                Toast.makeText(this, "Please enter 4 digit Pass!!", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.getUserLoginData(
                    session.mobile,
                    mBinding.editTextNumberPassword.text.toString(),
                    session.userType,
                    this,
                    (lastLocation)!!.latitude, (lastLocation)!!.longitude, address
                )
            }
        }
        observeData()
    }

    fun observeData() {
        viewModel.loading.observe(this, Observer {
            if (it) {
                mBinding.progressBar2.visibility = View.VISIBLE
            } else {
                mBinding.progressBar2.visibility = View.GONE

            }
        })
        viewModel.verifyList.observe(this, Observer {
            Log.d("Json data", "Json data" + it.jUserProfile.get(0).UserType)
            if (it.success) {
                if (it.jUserProfile.get(0).UserType.equals("A")) {
                    val intent = Intent(this, AdminActivity::class.java)
                    intent.putExtra("DriverList", it.jData as Serializable)
                    intent.putExtra("comingFrom", "Login")
                    startActivity(intent)
                    finish()
                } else if (it.jUserProfile.get(0).UserType.equals("F")) {
                    val intent = Intent(this, AdminActivity::class.java)
                    intent.putExtra("DriverList", it.jData as Serializable)
                    intent.putExtra("comingFrom", "Login")
                    startActivity(intent)
                    finish()
                } else {
                    getDriverData(
                        it.jUserProfile.get(0).MobileNo,
                        it.jUserProfile.get(0).Pwd,
                        it.jUserProfile.get(0).UserType
                    )
                }

            } else {
                Toast.makeText(this, "Something went wrong!!", Toast.LENGTH_SHORT).show()

            }

        })
    }

    fun getDriverData(mobileNo: String, otp: String, userType: String) {
        RetrofitClient.retrofit = null
        val userAPI = Apiuitils.testUrl
        val call = userAPI.verifyPWD(UpdateModel(mobileNo, otp, userType, (lastLocation)!!.latitude, (lastLocation)!!.longitude, address))
        call.enqueue(object : Callback<VerifyNewRespone> {
            override fun onResponse(
                call: Call<VerifyNewRespone>, response: Response<VerifyNewRespone>
            ) {
                if (response.code() == 200) {
                    session.keyToken = response.body()!!.jToken
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Invalid Password", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(
                call: Call<VerifyNewRespone>, t: Throwable
            ) {
//                loading.value = false

            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        if (fusedLocationClient?.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY, null
            ) != null
        ) {
            fusedLocationClient?.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)!!
                .addOnSuccessListener(this) { location: Location? ->
                    if (location != null) {
                        lastLocation = location
                        val latitude = (lastLocation)!!.latitude
                        val longitude = (lastLocation)!!.longitude
                        val geocoder = Geocoder(this, Locale.getDefault())
                        var addresses: List<Address?> = geocoder.getFromLocation(latitude, longitude, 1);
                        address = addresses[0]!!.getAddressLine(0)
                        val city = addresses[0]!!.locality
                        val state = addresses[0]!!.adminArea
                        val country = addresses[0]!!.countryName
                        val postalCode = addresses[0]!!.postalCode
                        val knownName =
                            addresses[0]!!.featureName // Only if available else return NULL

//                        Toast.makeText(
//                            this, address , Toast.LENGTH_LONG
//                        ).show();
//                        Toast.makeText(
//                            this, "$latitude, Longitude: $longitude", Toast.LENGTH_LONG
//                        ).show();
                    } else {
                        Log.w("LoginActivity", "getLastLocation is null :exception")
                        Toast.makeText(
                            this,
                            "No location detected. Make sure location is enabled on the device.",
                            Toast.LENGTH_LONG
                        ).show();
                    }
                }
        }
    }

}