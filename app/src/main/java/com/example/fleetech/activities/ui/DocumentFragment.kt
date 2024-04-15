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
import com.example.fleetech.adapter.DocAdapter
import com.example.fleetech.databinding.FragmentDocumentBinding
import com.example.fleetech.util.PdfClick
import com.example.fleetech.util.Session
import com.example.fleetech.viewModel.DocumentViewModel

class DocumentFragment : Fragment(), PdfClick {
    private lateinit var adapter: DocAdapter
    lateinit var viewModel: DocumentViewModel
    lateinit var sessionManager: Session
    private var _binding: FragmentDocumentBinding? = null
    private val mBinding get() = _binding!!
    lateinit var  pdfClick:PdfClick
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this).get(DocumentViewModel::class.java)

        _binding = FragmentDocumentBinding.inflate(inflater, container, false)
        sessionManager = Session(activity)
        pdfClick = this
        val root: View = mBinding.root
        driverDocs()
        mBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        mBinding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
        observerData()
        mBinding.ivBack.setOnClickListener{
   goBackToPreviousFragment()
        }

        return root
    }

    fun driverDocs() {
        viewModel.getDriverDoc(
            sessionManager.keyToken
        )
    }

    @SuppressLint("SetTextI18n")
    fun observerData() {
        viewModel.loading.observe(this, Observer {
            if (it) {
                mBinding.progressBar.visibility = View.VISIBLE
            } else {
                mBinding.progressBar.visibility = View.GONE

            }
        })
        viewModel.docList.observe(viewLifecycleOwner, Observer {
            if (it.success) {
// This will pass the ArrayList to our Adapter
                adapter = DocAdapter(it.jDocuments,pdfClick)
                // Setting the Adapter with the recyclerview
                mBinding.recyclerView.adapter = adapter

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