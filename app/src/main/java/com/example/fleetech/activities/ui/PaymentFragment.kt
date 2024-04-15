package com.example.fleetech.activities.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.fleetech.adapter.*

import com.example.fleetech.databinding.FragmentPaymentBinding
import com.example.fleetech.retrofit.model.Payment
import com.example.fleetech.retrofit.response.JMyPayment
import com.example.fleetech.util.PaymentClick
import com.example.fleetech.util.Session
import com.example.fleetech.viewModel.PaymentViewModel
import com.example.fleetech.viewModel.TripHistoryViewModel
import com.google.android.material.tabs.TabLayout
import java.util.ArrayList

class PaymentFragment : Fragment(), PaymentClick {

    lateinit var _binding: FragmentPaymentBinding
    private val binding get() = _binding!!


    lateinit var paymentClick: PaymentClick
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        paymentClick = this
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        tabLayout = findViewById<TabLayout>(R.id.tabLayout)
//        viewPager = findViewById<ViewPager>(R.id.viewPager)

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Trip Expenses"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Fuel Expenses"))
        binding.tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = MyAdapter(requireContext(), childFragmentManager, binding.tabLayout!!.tabCount)
        binding.viewPager.adapter = adapter
        binding.ivBack.setOnClickListener{
            goBackToPreviousFragment()
        }


        binding.viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout))

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.viewPager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

    }
//        tripDetailData()
//        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
//        binding.recyclerView.addItemDecoration(
//            DividerItemDecoration(
//                activity,
//                DividerItemDecoration.VERTICAL
//            )
//        )
  //      observerData()




    override fun paymentClick(position: Int, data: String) {

    }


    private fun setupExpandableListView() {

//        expandableListView.setOnGroupExpandListener { groupPosition ->
//            Toast.makeText(
//                activity,
//                (titleList as ArrayList<String>)[groupPosition] + " List Expanded.",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//
//        expandableListView.setOnGroupCollapseListener { groupPosition ->
//            Toast.makeText(
//                activity,
//                (titleList as ArrayList<String>)[groupPosition] + " List Collapsed.",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//
//        expandableListView.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
//            Toast.makeText(
//                activity,
//                "Clicked: " + (titleList as ArrayList<String>)[groupPosition] + " -> " + listData[(titleList as ArrayList<String>)[groupPosition]]!!.get(
//                    childPosition
//                ),
//                Toast.LENGTH_SHORT
//            ).show()
//            false
//        }
    }

    private fun goBackToPreviousFragment() {
        // Use FragmentManager to navigate back to the previous fragment
        parentFragmentManager.popBackStack()
    }


}