package com.example.fleetech.util

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.fleetech.retrofit.Apiuitils
import com.example.fleetech.retrofit.RetrofitClient
import com.example.fleetech.retrofit.model.LocationModel
import com.example.fleetech.retrofit.response.locationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MyService : Service() {
    val verifyList = MutableLiveData<locationResponse>()
    lateinit var session: Session

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        session = Session(this)
        if (intent != null) {


            onTaskRemoved(intent)
            val lattitude = intent.getStringExtra("Lattitude")
            val longitude = intent.getStringExtra("Longitude")

//         Handler(Looper.getMainLooper()).postDelayed(object : Runnable {
//            override fun run() {
//               getLatLong(lattitude, longitude)
//      Toast.makeText(
//      applicationContext, "This is a Service running in Background",
//      Toast.LENGTH_SHORT
//      ).show()
//            }
//         }, 60000)
//            val timer = Timer()
//            val hourlyTask: TimerTask = object : TimerTask() {
//                override fun run() {
//                    // your code here...
//                    getLatLong(lattitude, longitude)
//                }
//            }
//            timer.schedule(hourlyTask, 1L, 1000 * 60 * 60)
//
//        }
//            val mainHandler = Handler(Looper.myLooper()!!).postDelayed({
//                System.out.println("Thread : " + Thread.currentThread().name)
//                getLatLong(lattitude, longitude)
//
//            }, 600000)
//        }
            Handler(Looper.getMainLooper()).postDelayed({
                /* Create an Intent that will start the Menu-Activity. */
                getLatLong(lattitude, longitude)


            }, 600000)
        }
            return START_STICKY
        }

        override fun onBind(intent: Intent): IBinder? {
            // TODO: Return the communication channel to the service.
            throw UnsupportedOperationException("Not yet implemented")
        }

        override fun onTaskRemoved(rootIntent: Intent) {
            val restartServiceIntent = Intent(applicationContext, this.javaClass)
            restartServiceIntent.setPackage(packageName)
            startService(restartServiceIntent)
            super.onTaskRemoved(rootIntent)
        }

//   override fun onCreate() {
//      super.onCreate()
//      startForeground(1, Notification())
//   }

        private fun getLatLong(lattitude: String?, longitude: String?) {
            RetrofitClient.retrofit = null
            val userAPI = Apiuitils.testUrl
            val call = userAPI.driverLocation(
                LocationModel(
                    session.mobile,
                    session.keyLattitude,
                    session.keyLongitude
                )
            )
            call.enqueue(object : Callback<locationResponse> {
                override fun onResponse(
                    call: Call<locationResponse>,
                    response: Response<locationResponse>
                ) {
                    if (response.code() == 200) {
                        verifyList.postValue(response.body())
                        println("BBBB")

                    }
                }

                override fun onFailure(
                    call: Call<locationResponse>,
                    t: Throwable
                ) {

                }
            })

        }
    }