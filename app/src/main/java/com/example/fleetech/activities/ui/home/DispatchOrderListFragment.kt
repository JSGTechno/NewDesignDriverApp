package com.example.fleetech.activities.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fleetech.activities.MainActivity
import com.example.fleetech.adapter.DispatchOrderListAdapter
import com.example.fleetech.databinding.DispatchListLayoutBinding
import com.example.fleetech.retrofit.Apiuitils
import com.example.fleetech.retrofit.RetrofitClient
import com.example.fleetech.retrofit.response.VehicleReported
import com.example.fleetech.retrofit.response.VehicleReporteddataResponse
import com.example.fleetech.util.DispatchClick
import com.example.fleetech.util.Session
import com.example.fleetech.viewModel.DispatchOrderListViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DispatchOrderListFragment : Fragment(), DispatchClick {
    private lateinit var adapter: DispatchOrderListAdapter
    lateinit var viewModel: DispatchOrderListViewModel
    lateinit var sessionManager: Session
    private var _binding: DispatchListLayoutBinding? = null
    private val mBinding get() = _binding!!
    lateinit var pdfClick: DispatchClick
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this).get(DispatchOrderListViewModel::class.java)

        _binding = DispatchListLayoutBinding.inflate(inflater, container, false)
        sessionManager = Session(activity)
        pdfClick = this

        val root: View = mBinding.root
        driverDocs()
        mBinding.recyclerView2.layoutManager = LinearLayoutManager(activity)
        mBinding.recyclerView2.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
        observerData()

        return root
    }

    fun driverDocs() {
        viewModel.getDispatchOrderList(
            sessionManager.keyToken
        )
    }

    @SuppressLint("SetTextI18n")
    fun observerData() {
        viewModel.loading.observe(this, Observer {
            if (it) {
                mBinding.progressBar4.visibility = View.VISIBLE
            } else {
                mBinding.progressBar4.visibility = View.GONE

            }
        })
        viewModel.docList.observe(viewLifecycleOwner, Observer {
            if (it.success) {
// This will pass the ArrayList to our Adapter
                adapter = DispatchOrderListAdapter(it.jMyOrderList, pdfClick, requireContext())
                // Setting the Adapter with the recyclerview
                mBinding.recyclerView2.adapter = adapter

            } else {
                Toast.makeText(requireActivity(), "Something went wrong!!", Toast.LENGTH_SHORT)
                    .show()

            }

        })


    }

    override fun pdfCLick(position: Int, url: String) {
        JoinCircleDialog.newInstance("sss")
            .show(requireActivity().supportFragmentManager, JoinCircleDialog.TAG)
    }

    override fun reportedCLick(position: Int, url: String) {
        checkReportedFlag()
    }

    fun checkReportedFlag() {
        RetrofitClient.retrofit = null
        val userAPI = Apiuitils.testUrl
        val call = userAPI.vehicleReporteddata(
            "Bearer ${sessionManager.keyToken}",
            VehicleReported(sessionManager.keyOrderId)
        )
        call.enqueue(object : Callback<VehicleReporteddataResponse> {
            override fun onResponse(
                call: Call<VehicleReporteddataResponse>,
                response: Response<VehicleReporteddataResponse>
            ) {

                if (response.code() == 200) {
                    Toast.makeText(requireContext(), response.body()!!.jMsg, Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
//                    val jsonobject = JsonObject(response)
                    println("BBBBBBBBBBBBBBBBB ${response.body()}")
                } else {
                    Log.e("ERRRRRRR", "ERRRRRR" + response.code())
                }
            }

            override fun onFailure(
                call: Call<VehicleReporteddataResponse>,
                t: Throwable
            ) {
                Log.e("ERRRRRRR", "ERRRRRR1111" + t.localizedMessage)


            }
        })
    }


}