package com.example.fleetech.activities.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fleetech.activities.ui.home.HomeViewModel
import com.example.fleetech.adapter.ListAdapter
import com.example.fleetech.adapter.PaymentListAdapter
import com.example.fleetech.adapter.TripExpanseListAdapter
import com.example.fleetech.databinding.CurrentMonthFragBinding
import com.example.fleetech.retrofit.response.JMyPayment
import com.example.fleetech.retrofit.response.PmtDetail
import com.example.fleetech.retrofit.response.UpdateData
import com.example.fleetech.util.Session
import com.example.fleetech.viewModel.PaymentViewModel

class NextMonthFragment : Fragment() {
    private lateinit var adapter: TripExpanseListAdapter
    lateinit var mBinding: CurrentMonthFragBinding
    lateinit var viewModel: PaymentViewModel
    private lateinit var sessionManager: Session
    private var searchResults: ArrayList<PmtDetail> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this).get(PaymentViewModel::class.java)
        mBinding = CurrentMonthFragBinding.inflate(inflater, container, false)
        sessionManager = Session(activity)
        tripDetailData()
        observerData()
        val root: View = mBinding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tripDetailData()
        observerData()

    }
    override fun onResume() {
        super.onResume()
        tripDetailData()
        observerData()

    }
    fun tripDetailData() {
        viewModel.getTripHistoryDetail(
            sessionManager.keyToken
        )

    }

    @SuppressLint("SetTextI18n")
    fun observerData() {
        viewModel.loading.observe(this, Observer {

            if (it) {
                mBinding.progressBar6.visibility = View.VISIBLE
            } else {
                mBinding.progressBar6.visibility = View.GONE

            }
        })
        viewModel.paymentList.observe(viewLifecycleOwner, Observer {

            if (it.success) {


//                val expandableListView = binding.expandedLv
                println("Check List size:-" + it.jMyPayment.size)
//                for (item in it.jMyPayment) {
//                    searchResults.addAll(item.PmtDetail)
//                }
                adapter =
                    TripExpanseListAdapter(context,
                        it.jMyPayment as java.util.ArrayList<JMyPayment>?
                    )
                mBinding.recyclerView.setAdapter(adapter)
            } else {
                Toast.makeText(requireActivity(), "Something went wrong!!", Toast.LENGTH_SHORT)
                    .show()

            }

        })
    }
}
