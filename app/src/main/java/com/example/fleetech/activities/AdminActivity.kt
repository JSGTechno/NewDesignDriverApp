package com.example.fleetech.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fleetech.adapter.AdminAdapter
import com.example.fleetech.databinding.ActivityAdminBinding
import com.example.fleetech.retrofit.response.UpdateData
import com.example.fleetech.retrofit.response.VerifyData
import com.example.fleetech.util.AdminClickInterface
import com.example.fleetech.util.Session
import com.example.fleetech.viewModel.AdminViewModel


class AdminActivity : AppCompatActivity(), AdminClickInterface {

    private lateinit var DriverList: ArrayList<UpdateData>
    private var searchResults: ArrayList<UpdateData> = ArrayList()

    private lateinit var DriverListNew: ArrayList<VerifyData>
    private lateinit var adminInterFace: AdminClickInterface
    lateinit var viewModel: AdminViewModel
    private lateinit var sessionManager: Session
    lateinit var adapter: AdminAdapter
    private var searchString: String? = null
    var textLength = 0

    var comingFrom: String = ""
    private lateinit var mBinding: ActivityAdminBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        comingFrom = intent.getStringExtra("comingFrom") as String
        if (intent != null) {
            DriverList = intent?.getSerializableExtra("DriverList") as ArrayList<UpdateData>
        }
        viewModel = ViewModelProvider(this).get(AdminViewModel::class.java)
        mBinding = ActivityAdminBinding.inflate(layoutInflater)
        adminInterFace = this
        setContentView(mBinding.root)
        sessionManager = Session(applicationContext)
        // this creates a vertical layout Manager
        mBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        mBinding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        // This will pass the ArrayList to our Adapter
        adapter = AdminAdapter(DriverList, adminInterFace)
        // Setting the Adapter with the recyclerview
        mBinding.recyclerView.adapter = adapter
        observeData()
        searchData()
    }

    override fun onDriverClick(mobileNo: String, pwd: String, regNo: String, driverName: String) {
        println("Data to check ${ mobileNo} ${pwd}")
        sessionManager.keyDriverName= driverName
        viewModel.getDriverData(
            mobileNo,
            pwd,
            sessionManager.userType,
            this
        )
    }

    fun observeData() {
        viewModel.verifyList.observe(this, Observer {
            Log.d("Json data", "" + it.success)
            if (it.success) {
                sessionManager.keyToken = it.jToken
                sessionManager.setKeyInTime(it.jUserProfile.get(0).CheckInDate)
                sessionManager.setKeyLocation(it.jUserProfile.get(0).Location)


                println("Token--> ${it.jToken}")
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            } else {
                Toast.makeText(this,""+it.success, Toast.LENGTH_SHORT).show()

            }

        })
    }

    fun searchData() {
        val count: String = DriverList.size.toString()
        mBinding.totalCount.setText(count)
        mBinding.etSearch.setText("")
        //text change listner edittext searchBox
        mBinding.etSearch.addTextChangedListener(object : TextWatcher {
            @SuppressLint("NotifyDataSetChanged")
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                //get the text in the EditText
                searchString = mBinding.etSearch.getText().toString()
                textLength = searchString!!.length
                searchResults.clear()

//                //-----------------------------------------------------\\
                for (i in DriverList.indices) {
                    if (textLength <= DriverList.get(i).DriverName.length || textLength <= DriverList.get(
                            i
                        ).RegNo.length || textLength <= DriverList.get(i).MobileNo.length
                    ) {
                        if (DriverList.get(i).DriverName.trim().contains(
                                searchString!!.toUpperCase().trim { it <= ' ' })
                            || DriverList.get(i)
                                .RegNo.trim()
                                .contains(searchString!!.toUpperCase().trim { it <= ' ' })
                            || DriverList.get(i).MobileNo
                                .contains(searchString!!.toLowerCase().trim { it <= ' ' })
                        ) {


                            searchResults.add(DriverList.get(i))

                        }
                    }
                }
                adapter = AdminAdapter(searchResults, adminInterFace)
                mBinding.recyclerView.adapter = adapter
//                adapter.notifyDataSetChanged()
//                var text: String = s.toString()
//                var serchList: ArrayList<UpdateData>
//                serchList= DriverList
//                serchList.clear();
////                if (text.isEmpty()) {
////                    DriverList
////
////                } else {
//                    text = text.lowercase();
//                    for (i in DriverList.indices) {
//                        // Adapt the if for your usage
//                        if (DriverList.get(i).DriverName.lowercase().contains(text)) {
//                            serchList.add(DriverList.get(i));
//                        }
//                    }
//               // }
                adapter.notifyDataSetChanged();
            }


            //-----------------------------------------------------------//
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                println("before changed")
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun afterTextChanged(s: Editable) {

            }

        })
    }


}



