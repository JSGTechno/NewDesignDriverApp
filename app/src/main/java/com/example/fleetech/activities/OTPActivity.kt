package com.example.fleetech.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fleetech.R
import com.example.fleetech.databinding.ActivityMain3Binding
import com.example.fleetech.databinding.ActivityOtpactivityBinding
import com.example.fleetech.viewModel.OTPViewModel
import com.example.fleetech.viewModel.UserRegisterViewModel

class OTPActivity : AppCompatActivity() {
    private var mobileNo: String? = null
    private lateinit var mBinding: ActivityOtpactivityBinding
    lateinit var viewModel: OTPViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityOtpactivityBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(OTPViewModel::class.java)
        mobileNo = intent.getStringExtra("mobileNo");
        setContentView(mBinding.root)
        mBinding.resendOTPTV.setOnClickListener{
            if (mBinding.editTextNumberPassword.length() < 4) {
                Toast.makeText(this, "Please enter Resend OTP!!", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.getUserResendOTPDATA(
                    mobileNo!!,
                    mBinding.editTextNumberPassword.text.toString(),
                    "DeviceID",
                    "ModelNo",
                )
            }
        }
        mBinding.btnOTP.setOnClickListener {

            if (mBinding.editTextNumberPassword.length() < 4) {
                Toast.makeText(this, "Please enter OTP!!", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.getUserOTPDATA(mobileNo.toString(),
                    mBinding.editTextNumberPassword.text.toString(),
                    "DeviceID",
                    "ModelNo",
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
        viewModel.otpList.observe(this, Observer {
            if (it.success) {
                val intent = Intent(this, PasswordActivity::class.java)
                intent.putExtra("mobileNo",mobileNo)
                startActivity(intent)
            } else {
                Toast.makeText(this, "wrong", Toast.LENGTH_SHORT).show()


            }

        })
    }

}