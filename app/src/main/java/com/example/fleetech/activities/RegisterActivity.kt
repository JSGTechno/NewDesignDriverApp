package com.example.fleetech.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.Settings.Secure
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fleetech.base.BaseActivity
import com.example.fleetech.databinding.ActivityRegisterBinding
import com.example.fleetech.util.Session
import com.example.fleetech.viewModel.UserRegisterViewModel


class RegisterActivity : BaseActivity() {
    private lateinit var mBinding: ActivityRegisterBinding
    private val TAG = "RegisterActivity"
    lateinit var viewModel: UserRegisterViewModel
   lateinit var sessionManager :Session
    @SuppressLint("HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegisterBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(UserRegisterViewModel::class.java)
        sessionManager = Session(applicationContext)
        setContentView(mBinding.root)
        //get viewmodel instance using MyViewModelFactory
        viewModel.errorMessage.observe(this, Observer {
            Log.d(TAG, "errorMessage: $it")
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
        val android_id = Secure.getString(applicationContext.getContentResolver(), Secure.ANDROID_ID)

        mBinding.button.setOnClickListener {
            if (mBinding.editTextPhone.text.isNotEmpty()) {
                viewModel.getUserRegistrationData(
                    mBinding.editTextPhone.text.toString(),
                    android_id,
                    android.os.Build.MODEL,
                    "fcm"
                )
            } else {
                Toast.makeText(this, "Please enter mobile no!!", Toast.LENGTH_SHORT).show()
            }
        }

        observeData()
    }

    fun observeData() {
        viewModel.loading.observe(this, Observer {
            if (it) {
                mBinding.progressBar.visibility = View.VISIBLE
            } else {
                mBinding.progressBar.visibility = View.GONE
            }
        })
        viewModel.movieList.observe(this, Observer {
            if (it.success) {
                sessionManager.mobile =it.jData.get(0).MobileNo
                val intent = Intent(this, OTPActivity::class.java)
                intent.putExtra("mobileNo",it.jData.get(0).MobileNo)
                startActivity(intent)

            } else {
                Toast.makeText(this, it.jData.get(0).Msg, Toast.LENGTH_SHORT).show()

            }

        })
    }

    override fun onBackPressed() {
        super.onBackPressed()

    }
}