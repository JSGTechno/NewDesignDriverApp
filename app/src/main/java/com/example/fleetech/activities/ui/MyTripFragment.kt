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
import androidx.viewpager.widget.ViewPager
import com.example.fleetech.R
import com.example.fleetech.adapter.DocAdapter
import com.example.fleetech.adapter.MyTripHistoryAdapter
import com.example.fleetech.databinding.FragmentMyTripBinding
import com.example.fleetech.databinding.FragmentProfileBinding
import com.example.fleetech.util.Session
import com.example.fleetech.viewModel.ProfileViewModel
import com.example.fleetech.viewModel.TripHistoryViewModel
import com.google.android.material.tabs.TabLayout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyTripFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyTripFragment : Fragment() {
    private lateinit var adapter: MyTripHistoryAdapter
    private var _binding: FragmentMyTripBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: TripHistoryViewModel
    private lateinit var sessionManager: Session

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this).get(TripHistoryViewModel::class.java)

        _binding = FragmentMyTripBinding.inflate(inflater, container, false)
        sessionManager = Session(activity)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tripDetailData()
        binding.recyclerView2.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView2.addItemDecoration(
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
    fun tripDetailData() {
        viewModel.getTripHistoryDetail(
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
        viewModel.tripHistoryList.observe(viewLifecycleOwner, Observer {
            if (it.success) {
// This will pass the ArrayList to our Adapter
                adapter = MyTripHistoryAdapter(it.jMyTrip)
                // Setting the Adapter with the recyclerview
                binding.recyclerView2.adapter = adapter
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