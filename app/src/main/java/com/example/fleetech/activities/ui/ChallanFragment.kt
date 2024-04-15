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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fleetech.R
import com.example.fleetech.adapter.ChallanAdapter
import com.example.fleetech.adapter.SettlementAdapter
import com.example.fleetech.databinding.DispatchListLayoutBinding
import com.example.fleetech.databinding.FragmentChallanBinding
import com.example.fleetech.databinding.FragmentSettleMentBinding
import com.example.fleetech.util.Session
import com.example.fleetech.viewModel.ChallanViewModel
import com.example.fleetech.viewModel.SettleMentViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChallanFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChallanFragment : Fragment() {
    private lateinit var adapter: ChallanAdapter
    private var _binding: FragmentChallanBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: ChallanViewModel
    private lateinit var sessionManager: Session



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this).get(ChallanViewModel::class.java)

        _binding = FragmentChallanBinding.inflate(inflater, container, false)
        sessionManager = Session(activity)
        val root: View = binding.root
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mySettlementData()
        binding.recyclerViewChallan.layoutManager = LinearLayoutManager(activity)
        binding.recyclerViewChallan.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
        observerData()
        binding.ivBack.setOnClickListener{
            goBackToPreviousFragment()
        }
    }
    fun mySettlementData() {
        viewModel.getMyChallan(
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
        viewModel.mychallanList.observe(viewLifecycleOwner, Observer {
            if (it.success) {
// This will pass the ArrayList to our Adapter
                adapter = ChallanAdapter(it.jMyChallanList)
                // Setting the Adapter with the recyclerview
                binding.recyclerViewChallan.adapter = adapter
            } else {
                Toast.makeText(requireActivity(), "Something went wrong!!", Toast.LENGTH_SHORT)
                    .show()

            }

        })


    }

    private fun goBackToPreviousFragment() {
        // Use FragmentManager to navigate back to the previous fragment
        parentFragmentManager.popBackStack()
    }


}