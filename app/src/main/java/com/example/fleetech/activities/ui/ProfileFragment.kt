package com.example.fleetech.activities.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fleetech.R
import com.example.fleetech.activities.ui.home.HomeViewModel
import com.example.fleetech.databinding.ContentMainMapWithTextBinding
import com.example.fleetech.databinding.FragmentProfileBinding
import com.example.fleetech.util.Session
import com.example.fleetech.viewModel.ProfileViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {

    lateinit var viewModel: ProfileViewModel

    lateinit var sessionManager: Session
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        sessionManager = Session(activity)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tripDetailData()
        observerData()
    }

    fun tripDetailData() {
        viewModel.getDriverProfile(
            sessionManager.keyToken
        )
    }

    @SuppressLint("SetTextI18n")
    fun observerData() {
        viewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressBar3.visibility = View.VISIBLE
            } else {
                binding.progressBar3.visibility = View.GONE

            }
        })
        viewModel.profileList.observe(viewLifecycleOwner, Observer {
            if (it.success) {

                binding.driverName.text = "Name - ${it.jProfile.get(0).DriverName}"
                binding.contact.text = "Father Name - ${it.jProfile.get(0).FatherName}"
                binding.dob.text = "DOB - ${it.jProfile.get(0).DOB}"
                binding.address.text = "Address ${it.jProfile.get(0).Address}"


            } else {
                Toast.makeText(requireActivity(), "Something went wrong!!", Toast.LENGTH_SHORT)
                    .show()

            }

        })


    }


}