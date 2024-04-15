package com.example.fleetech.util

import android.app.Application
import android.content.Context
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener


/**
 *  Main Application create instance of BLe class
 *  Create FCM device token
 *  Create Crashlytics class referance
 * **/

class MainApplication : Application() {


    companion object {
        private lateinit var instance: MainApplication
        fun get(): MainApplication = instance

        @JvmStatic
        fun instance(): MainApplication {
            return instance
        }

    }

    override fun onCreate() {
        super.onCreate()
        instance = this

       // init()
        val handler =
            Thread.getDefaultUncaughtExceptionHandler()

        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            handler.uncaughtException(thread, throwable)
        }
    }

    fun getContext(): Context {
        return applicationContext
    }

    private fun init() {
        /*
         * Initializes the iHealth devices manager. Can discovery available iHealth devices nearby
         * and connect these devices through iHealthDevicesManager.
         */
     //   iHealthDevicesManager.getInstance().init(this, Log.VERBOSE, Log.VERBOSE)
    }

    fun logOut() {
      //  AppManager.instance().finishAllActivity()
    }


}