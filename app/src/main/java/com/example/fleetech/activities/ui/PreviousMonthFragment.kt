package com.example.fleetech.activities.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fleetech.activities.ui.home.HomeViewModel
import com.example.fleetech.adapter.ListAdapter
import com.example.fleetech.databinding.CurrentMonthFragBinding

class PreviousMonthFragment  : Fragment() {
    lateinit var mBinding: CurrentMonthFragBinding
    private val mNicolasCageMovies = listOf(
        MyTrip("1", "3444", "DEL", "13/04", "Y", ""),
        MyTrip("2", "3443", "DEL", "13/04", "N", ""),
        MyTrip("3", "3444", "DEL", "13/04", "Y", ""),
        MyTrip("4", "3442", "DEL", "13/04", "N", ""),
        MyTrip("5", "3444", "DEL", "13/04", "Y", ""),
        MyTrip("6", "3446", "DEL", "13/04", "N", ""),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        mBinding = CurrentMonthFragBinding.inflate(inflater, container, false)
//        mBinding.recyclerView.apply {
//            // set a LinearLayoutManager to handle Android
//            // RecyclerView behavior
//            layoutManager = LinearLayoutManager(activity)
//            // set the custom adapter to the RecyclerView
//            adapter = ListAdapter(mNicolasCageMovies)
//        }
//
//        mBinding.recyclerView.addItemDecoration(
//            DividerItemDecoration(
//                context,
//                LinearLayoutManager.VERTICAL
//            )
//        )
        val root: View = mBinding.root

        return root
    }

    companion object {
        fun newInstance(): CurrentMonthFragment = CurrentMonthFragment()
    }
}