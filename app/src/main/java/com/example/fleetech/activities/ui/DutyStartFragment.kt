package com.example.fleetech.activities.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fleetech.R
import com.example.fleetech.activities.ui.home.AssignOrderListFragment
import com.example.fleetech.activities.ui.home.DispatchOrderListFragment
import com.example.fleetech.activities.ui.home.HomeViewModel
import com.example.fleetech.activities.ui.home.JoinCircleDialog
import com.example.fleetech.databinding.FragmentDutyStartBinding
import com.example.fleetech.databinding.FragmentMyTripBinding
import com.example.fleetech.util.Session
import com.example.fleetech.viewModel.DutyStartModel
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DutyStartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DutyStartFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentDutyStartBinding? = null
    private val binding get() = _binding!!
    private lateinit var sessionManager: Session
    lateinit var viewModel: DutyStartModel
    private lateinit var TripStatus: String
    private lateinit var FleetLocation: String
    private lateinit var countDownTimer: CountDownTimer ;
    var Destination_1 : Double = 0.0
    var Destination_2 : Double = 0.0
    var Source_Lat : Double = 0.0
    var Source_Long : Double = 0.0

    val PERMISSIONS_MULTIPLE_REQUEST = 123

   /* override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProvider(this).get(DutyStartModel::class.java)
        _binding = FragmentDutyStartBinding.inflate(inflater, container, false)
        sessionManager = Session(activity)
        val root: View = binding.root
        // Inflate the layout for this fragment
        binding.walletTv.text = "₹ " + "200"
        binding.ivBack.setOnClickListener{
         //   requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                // Handle back press event
                goBackToPreviousFragment()
           // }
        }

        tripDetailData()
        observerData()
        timer()
        binding.mapLoc.setOnClickListener{

            navigateToFragmentA()
        }
        binding.mapLocDelivery.setOnClickListener{

            navigateToFragmentB()
        }
        binding.reportedFlag.setOnClickListener {
            reportedVehicleFlag()

        }

        observerReportedflagData()

        binding.podUpload.setOnClickListener {
            if (sessionManager.keyPodtype.equals("PODTYPE")) {
                checkPermission()
                JoinCircleDialog.newInstance("sss")
                    .show(requireActivity().supportFragmentManager, JoinCircleDialog.TAG)
            } else if (sessionManager.keyPodtype.equals("ASSIGNTYPE")) {
                loadFragment(AssignOrderListFragment())

            } else if (sessionManager.keyPodtype.equals("DISPATCHTYPE")) {
                loadFragment(DispatchOrderListFragment())

            } else if (sessionManager.keyPodtype.equals("LRTYPE")) {
                checkPermission()
                JoinCircleDialog.newInstance("sss")
                    .show(requireActivity().supportFragmentManager, JoinCircleDialog.TAG)
            }
        }


        return root
    }

    fun tripDetailData() {
        viewModel.getDriverTripDetails(
            sessionManager.keyToken
        )
    }
    fun timer(){



        }

    fun reportedVehicleFlag() {
        Log.i("TAG","call " + "trueee")
        viewModel.checkReportedFlag(
            sessionManager.keyToken, sessionManager.keyOrderId
        )
    }

    fun observerData() {


        viewModel.loading.observe(this, Observer {
           /* if (it) {
                binding.progressBarpodstatus.visibility = View.VISIBLE
            } else {
                binding.progressBarpodstatus.visibility = View.GONE

            }*/
        })
        viewModel.tripList.observe(viewLifecycleOwner, Observer {
            Log.d("Json data Home", "" + it.jTripData)

            if (it.success) {
                TripStatus = it.jTripData.get(0).TripStatus
                FleetLocation = it.jTripData.get(0).FleetLocation
                binding.vehicleStatus.text = it.jTripData.get(0).TripStatus

              //  binding.tvLoadingaddress.text = it.jTripData.get(0).Address
                binding.gpsLocationTv.text = it.jTripData.get(0).FleetLocation
                Log.i("Data POD", "Data POD" + it.jTripData)

                if (it.jTripData.get(0).TripStatus.equals("Assign") && it.jTripData.get(0).MultiRequestFlag.equals("Y")) {
                    sessionManager.keyPodtype = "ASSIGNTYPE"
                    binding.podUpload.visibility = View.VISIBLE

                    //show assign list
                } else if (it.jTripData.get(0).TripStatus.equals("EnRoute") && it.jTripData.get(0).MultiRequestFlag.equals("Y")) {
                    binding.podUpload.visibility = View.VISIBLE
                    //show dispatch list
                    sessionManager.keyPodtype = "DISPATCHTYPE"

                } else {
                    binding.podUpload.visibility = View.VISIBLE

                }


                if (it.jTripData.get(0).PODFlag.equals("Y")) {
                    binding.podUpload.visibility = View.VISIBLE
                    sessionManager.keyPodtype = "PODTYPE"

                }
                if (it.jTripData.get(0).LoadingSlipFlag.equals("Y")) {
                    binding.podUpload.isEnabled = true
                    binding.podUpload.visibility = View.VISIBLE
                    sessionManager.keyPodtype = "LRTYPE"

                }

                if (it.jTripData.get(0).DocFlag.equals("Y")) {
                    if (it.jTripData.get(0).ReportedFlag.equals("Y")) {
                        binding.reportedFlag.visibility = View.VISIBLE
                        binding.podUpload.visibility = View.GONE
                    } else {
                        binding.podUpload.visibility = View.VISIBLE
                        binding.reportedFlag.visibility = View.GONE
                    }


                } else {
                    binding.podUpload.visibility = View.GONE
                    binding.reportedFlag.visibility = View.GONE
                }

                sessionManager.setKeyOrderId(it.jTripData.get(0).OrderID.toString())


                binding.walletTv.text = "₹ " + it.jWalletBalance



                val vehicleNo: String = it.jTripData.get(0).VehicleNo
                val vehicleCategory: String = it.jTripData.get(0).VehicleCategory
                binding.vehicleNoTv.text = vehicleNo

                binding.vehicleCategoryTv.text = vehicleCategory
                binding.dispatchDateTv.text =
                    "${it.jTripData.get(0).DispatchDate} ${"-"} ${it.jTripData.get(0).DispatchTime}"
                binding.expectedarrivaldate.text =
                    "${it.jTripData.get(0).DeliveryDate} ${"-"} ${it.jTripData.get(0).DeliveryTime}"

                binding.tvLoadingaddressText.text = it.jTripData.get(0).AddressType
                binding.orderId.text = "Order ID- ${it.jTripData.get(0).OrderID.toString()}"
                var sourceAddre = it.jTripData.get(0).Source
                var destinationAddre = it.jTripData.get(0).Destination
                binding.tvTofrom.text = "${sourceAddre}${" - "}${destinationAddre}"
                binding.tvLoadingaddressText.text = it.jTripData.get(0).PickUpAddress//"${sourceAddre}"
                binding.tvDeliveryaddressText.text =  it.jTripData.get(0).DeliveryAddress//"${destinationAddre}"
                Destination_1 = it.jTripData.get(0).DeliveryLat
                Destination_2 = it.jTripData.get(0).DeliveryLong
                Source_Lat = it.jTripData.get(0).PickupLat
                Source_Long = it.jTripData.get(0).PickupLong

                Log.i("TAG","data_check_trip" + Source_Lat + " : " + it.jTripData.get(0).PendingHoursFlag)

                //binding.fleetStatus.text = it.jTripData.get(0).FleetStatus
                sessionManager.keyWalletBalance = it.jWalletBalance
                 if (it.jTripData.get(0).TripHours !=null) {
                     binding.totalhourtv.text = it.jTripData.get(0).TripHours
                     binding.pendinghourtv.text = it.jTripData.get(0).PendingTime
                 }


                if (it.jTripData.get(0).PendingHours != null) {
                val totalHours = timeStringToMilliseconds(it.jTripData.get(0).PendingHours)
                    sessionManager.keyGalleryImage =  it.jTripData.get(0).GalleryImage
                Log.i("TAG","timer" + " : " + totalHours + " : " + it.jTripData.get(0).GalleryImage);
                    val totalTimeInMillis: Long = totalHours //it.jTripData.get(0).PendingTime.toLong()
                    // 1000 * 60 * 10 // 10 minutes

                    countDownTimer = object : CountDownTimer(totalTimeInMillis, 1000) {
                        override fun onTick(millisUntilFinished: Long) {
                            val hours = millisUntilFinished / 3600000
                            val minutes = (millisUntilFinished % 3600000) / 60000
                            val seconds = (millisUntilFinished % 60000) / 1000
                           /* val minutes = millisUntilFinished / 1000 / 60
                            val seconds = (millisUntilFinished / 1000) % 60*/
                            binding.timerTextView.text =
                                String.format("%02d",hours/*, minutes, seconds*/)
                            binding.timerMinutes.text = String.format("%02d",minutes)
                            binding.timerSec.text = String.format("%02d",seconds)
                        }

                        override fun onFinish() {

                            binding.timerTextView.text = "00"
                            binding.timerMinutes.text = "00"
                            binding.timerSec.text = "00"
                            // Handle timer finish actions here
                        }
                    }

                    countDownTimer.start()
                }else{

                    countDownTimer.start()
                    binding.timerTextView.text = "00"
                    binding.timerMinutes.text = "00"
                    binding.timerSec.text = "00"

                }

            } else {
                Toast.makeText(requireActivity(), "Something went wrong!!", Toast.LENGTH_SHORT)
                    .show()

                //   val totalTimeInMillis: Long = 1000 * 60 * 10 // 10 minutes
                countDownTimer = object : CountDownTimer(1000 * 60 * 10, 1000) {
                    override fun onTick(millisUntilFinished: Long) {

                    }

                    override fun onFinish() {
                        // Perform actions when countdown finishes
                    }
                }

            }
        })


    }

    fun observerReportedflagData() {
        viewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressBarpodstatus.visibility = View.VISIBLE
            } else {
                binding.progressBarpodstatus.visibility = View.GONE

            }
        })
        viewModel.reportedfalgData.observe(viewLifecycleOwner, Observer {
            if (it.success) {
                Toast.makeText(requireActivity(), it.jMsg, Toast.LENGTH_SHORT).show()
                //tripDetailData() yogesh comment
                //observerData()

            } else {
                Toast.makeText(requireActivity(), "Something went wrong!!", Toast.LENGTH_SHORT)
                    .show()

            }

        })


    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DutyStartFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DutyStartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()

           try {
           countDownTimer.cancel()
    } catch (e: Exception) {
               // Handle general exception
               println("An error occurred: ${e.message}")
           }
    }


    fun timeStringToMilliseconds(timeString: String): Long {
        val parts = timeString.split(":")
        val hours = parts[0].toLong()
        val minutes = parts[1].toLong()
        val seconds = parts[2].toLong()

        val totalTimeInMillis = (hours * 3600 + minutes * 60 + seconds) * 1000
        return totalTimeInMillis
    }




    override fun onResume() {
        super.onResume()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            goBackToPreviousFragment()
        }
    }

    private fun goBackToPreviousFragment() {
        // Use FragmentManager to navigate back to the previous fragment
        parentFragmentManager.popBackStack()
    }

    private fun navigateToFragmentB() {
        val bundle = Bundle().apply {
            putDouble("Destionation_lat", Destination_1) // Put parameters you want to pass
            putDouble("Destionation_long", Destination_2)
            putDouble("Source_lat", Source_Lat)
            putDouble("Source_long", Source_Long)
        }

        val intent = Intent(activity, MapShowActivity::class.java)
        intent.putExtras(bundle);
        startActivity(intent)
       /* val bundle = Bundle().apply {
            putDouble("Destionation_lat", Destination_1) // Put parameters you want to pass
            putDouble("Destionation_long", Destination_2)
            putDouble("Source_lat", Source_Lat)
            putDouble("Source_long", Source_Long)
        }
        val fragmentB = MapFragment()
        fragmentB.arguments = bundle
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content_frame, fragmentB)
        transaction.addToBackStack(null) // Optional: Add transaction to back stack
        transaction.commit()*/
    }

    private fun navigateToFragmentA() {
        val bundle = Bundle().apply {
            putDouble("Destionation_lat", Source_Lat) // Put parameters you want to pass
            putDouble("Destionation_long", Source_Long)
            putDouble("Source_lat", Destination_1)
            putDouble("Source_long", Source_Long)
        }

        val intent = Intent(activity, MapShowActivity::class.java)
        intent.putExtras(bundle);
        startActivity(intent)
    /*    val fragmentB = MapFragment()
        fragmentB.arguments = bundle
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content_frame, fragmentB)
        transaction.addToBackStack(null) // Optional: Add transaction to back stack
        transaction.commit()*/
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) +
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) +
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) ||
                ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) ||
                ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.CAMERA
                )
            ) {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    "Please Grant Permissions to upload profile photo",
                    Snackbar.LENGTH_INDEFINITE
                ).setAction(
                    "ENABLE"
                ) {
                    requestPermissions(
                        arrayOf(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA
                        ),
                        PERMISSIONS_MULTIPLE_REQUEST
                    )
                }.show()
            } else {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                    ),
                    PERMISSIONS_MULTIPLE_REQUEST
                )
            }
        } else {
            // write your logic code if permission already granted
            // selectImage()
        }
    }

    private fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction = requireFragmentManager().beginTransaction()
        transaction.replace(R.id.content_frame, fragment)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.addToBackStack("Fragment");
        transaction.commit()
    }

}