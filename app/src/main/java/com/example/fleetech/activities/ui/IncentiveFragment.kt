package com.example.fleetech.activities.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fleetech.R
import com.example.fleetech.activities.ui.home.HomeViewModel
import com.example.fleetech.adapter.IncentiveListAdapter
import com.example.fleetech.adapter.PaymentListAdapter
import com.example.fleetech.databinding.FragmentIncentiveBinding
import com.example.fleetech.databinding.FragmentPaymentBinding
import com.example.fleetech.retrofit.model.Incentive
import com.example.fleetech.retrofit.model.Payment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [IncentiveFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class IncentiveFragment : Fragment() {

lateinit var mBinding:FragmentIncentiveBinding
    private val mNicolasCageMovies = listOf(
        Incentive("1", "12/02","3444","DEL-BLR","3444"),
        Incentive("2", "12/02","3444","DEL-BLR","3444"),
        Incentive("3", "12/02","3444","DEL-BLR","3444"),
        Incentive("4", "12/02","3444","DEL-BLR","3444"),
        Incentive("5", "12/02","3444","DEL-BLR","3444"),
        Incentive("6", "12/02","3444","DEL-BLR","3444"),
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        mBinding = FragmentIncentiveBinding.inflate(inflater, container, false)
        mBinding.recyclerView.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            adapter = IncentiveListAdapter(mNicolasCageMovies)
        }
        mBinding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )
        val root: View = mBinding.root

        return root
    }

}