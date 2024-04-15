package com.example.fleetech.activities.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fleetech.R
import com.example.fleetech.adapter.MyTripHistoryAdapter
import com.example.fleetech.adapter.SettlementAdapter
import com.example.fleetech.databinding.FragmentMyTripBinding
import com.example.fleetech.databinding.FragmentSettleMentBinding
import com.example.fleetech.util.PdfClick
import com.example.fleetech.util.Session
import com.example.fleetech.viewModel.SettleMentViewModel
import com.example.fleetech.viewModel.TripHistoryViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettleMentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettleMentFragment : Fragment(), PdfClick {
    private lateinit var adapter: SettlementAdapter
    private var _binding: FragmentSettleMentBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: SettleMentViewModel
    private lateinit var sessionManager: Session
    lateinit var  pdfClick:PdfClick

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this).get(SettleMentViewModel::class.java)

        _binding = FragmentSettleMentBinding.inflate(inflater, container, false)
        sessionManager = Session(activity)
        pdfClick = this
        binding.ivBack.setOnClickListener{
            goBackToPreviousFragment()
        }

        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mySettlementData()
        binding.recyclerViewSettlement.layoutManager = LinearLayoutManager(activity)
        binding.recyclerViewSettlement.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
        observerData()
    }
    fun mySettlementData() {
        viewModel.getMySettlementDetail(
            sessionManager.keyToken
        )
    }

    @SuppressLint("SetTextI18n")
    fun observerData() {
        viewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressBar4.visibility = View.VISIBLE
            } else {
                binding.progressBar4.visibility = View.GONE

            }
        })
        viewModel.settlementList.observe(viewLifecycleOwner, Observer {
            if (it.success) {
// This will pass the ArrayList to our Adapter
                adapter = SettlementAdapter(it.jMySettlementList,pdfClick)
                // Setting the Adapter with the recyclerview
                binding.recyclerViewSettlement.adapter = adapter
            } else {
                Toast.makeText(requireActivity(), "Something went wrong!!", Toast.LENGTH_SHORT)
                    .show()

            }

        })


    }


    override fun pdfCLick(position: Int, url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    private fun goBackToPreviousFragment() {
        // Use FragmentManager to navigate back to the previous fragment
        parentFragmentManager.popBackStack()
    }


}