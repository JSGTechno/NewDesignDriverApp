package com.example.fleetech.activities.ui.home

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
import com.example.fleetech.adapter.AssignOrderListAdapter
import com.example.fleetech.databinding.AssignOrderListBinding
import com.example.fleetech.util.PdfClick
import com.example.fleetech.util.Session
import com.example.fleetech.viewModel.AssignOrderListViewModel
import com.nrv.fleetech.activities.ui.home.JoinCircleDialog

class AssignOrderListFragment : Fragment(), PdfClick {
    private lateinit var adapter: AssignOrderListAdapter
    lateinit var viewModel: AssignOrderListViewModel
    lateinit var sessionManager: Session
    private var _binding: AssignOrderListBinding? = null
    private val mBinding get() = _binding!!
    lateinit var  pdfClick:PdfClick
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this).get(AssignOrderListViewModel::class.java)

        _binding = AssignOrderListBinding.inflate(inflater, container, false)
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
        viewModel.getAssignOrderList(
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
                adapter = AssignOrderListAdapter(it.jMyOrderList,pdfClick,requireContext())
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


}