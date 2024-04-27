package com.example.fleetech.activities.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Looper
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Base64
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.fleetech.R
import com.example.fleetech.activities.ui.*
import com.example.fleetech.databinding.ContentMainMapWithTextBinding
import com.example.fleetech.retrofit.response.PODResponse
import com.example.fleetech.util.AddressUtils
import com.example.fleetech.util.PermissionClass
import com.example.fleetech.util.Session
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.snackbar.Snackbar
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import android.location.Address
import com.nrv.fleetech.activities.ui.home.JoinCircleDialog


class HomeFragment : Fragment(), OnMapReadyCallback, LocationListener,
    GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
    JoinCircleDialog.InterfaceCommunicator {

    lateinit var mBitmapValue: Bitmap
    private lateinit var SourceAdd: String
    private var frontPodEncoded: String = ""
    private var backPodEncoded: String = ""
    private var checkPODupload: Boolean = false
    private var checkLRupload: Boolean = false


    private lateinit var LRNO: String
    private lateinit var OrderID: String
    private lateinit var DestinationAdd: String
    private lateinit var FleetLocation: String
    private lateinit var ConAddress: String
    private lateinit var FleetStatus: String
    private lateinit var AddressType: String
    private lateinit var ContactMobile: String
    private lateinit var lattitude: String
    private lateinit var longitude: String
    private lateinit var TripStatus: String
    private val imgBackStr: String? = ""
    private var imgFrontStr: String? = ""
    private var currentType: String = "0"
    lateinit var popupWindowBackPod: PopupWindow
    lateinit var popupWindowMainPOD: PopupWindow
    var destination: File = File("")
    var currentPhotoPath: String = ""
    var currentPhotoPathBack: String = ""
    val balance: String = ""
    lateinit var mBitmapValueFront: Bitmap
    lateinit var mBitmapValueBack: Bitmap

    var imageViewFlag = false
    var expandView = false
    lateinit var photoURI: Uri
    private lateinit var file: File
    val PERMISSIONS_MULTIPLE_REQUEST = 123

    private val FINAL_TAKE_PHOTO = 1
    private val FINAL_CHOOSE_PHOTO = 2
    private var REQUEST_CODE_IMAGE_CAPTURE = 122
    private lateinit var popupWindowFrontPod: PopupWindow
    private var mMap: GoogleMap? = null
    internal lateinit var mLastLocation: Location
    internal lateinit var mLocationResult: LocationRequest
    internal lateinit var mLocationCallback: LocationCallback
    internal var mCurrLocationMarker: Marker? = null
    internal var mGoogleApiClient: GoogleApiClient? = null
    internal lateinit var mLocationRequest: LocationRequest
    internal var mFusedLocationClient: FusedLocationProviderClient? = null
    private var _binding: ContentMainMapWithTextBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: HomeViewModel
    lateinit var sessionManager: Session
    val uploadPODDoc = MutableLiveData<PODResponse>()
    var cityName : String = ""
    var address : String = ""



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = ContentMainMapWithTextBinding.inflate(inflater, container, false)
        sessionManager = Session(activity)
        PermissionClass.checkAndRequestPermissions(activity);
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            OrderID = requireArguments()!!.getString("OrderID").toString()
            LRNO = requireArguments().getString("LRNO").toString()
            SourceAdd = requireArguments()!!.getString("Source").toString()
            DestinationAdd = requireArguments()!!.getString("Destination").toString()
            TripStatus = requireArguments()!!.getString("TripStatus").toString()
            ConAddress = requireArguments()!!.getString("ConAddress").toString()
            ContactMobile = requireArguments()!!.getString("ContactMobile").toString()
            FleetLocation = requireArguments()!!.getString("FleetLocation").toString()
            FleetStatus = requireArguments()!!.getString("FleetStatus").toString()
            AddressType = requireArguments()!!.getString("AddressType").toString()

        }
        val mapFragment =
            activity?.supportFragmentManager?.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        //tripDetailData() yogesh comment
       // observerData()
        //  checkAccountBalance()
        // observerDataBalance()
        binding.progressBarpodstatus.visibility = View.GONE
        binding.accountBalance.setText(sessionManager.keyWalletBalance)

        binding.reportedFlag.setOnClickListener {
            reportedVehicleFlag()

        }
        observerReportedflagData()



        try {
            //data get on
            val context = requireContext()
            val addressUtils = AddressUtils(context)
            address = addressUtils.getAddressFromLocation(
                sessionManager.keyLattitude.toDouble(),
                sessionManager.keyLongitude.toDouble()
            )

            Log.i("TAG", "address" + address)

            //  val address_1 = "F4J2+G27 The Solitaire Plaza, Mehrauli-Gurgaon Rd, Sikanderpur, Sector 26, Gurugram, Haryana 122002, India"
            val (city, state) = getAddressDetails(context, address)
            cityName = "$city"
            println("City: $city")
            println("State: $state")
        }catch (e: Exception) {
            // Exception handling code
            println("An exception occurred: ${e.message}")
        }





       /* val parts = address.split(",")

        // Extract state and city from the last part of the address
        val stateAndCity = parts.lastOrNull()?.trim()

        // Use regular expression to find state and city
        val stateRegex = Regex("(\\w+),\\s*(\\w+)$")
        val matchResult = stateRegex.find(stateAndCity ?: "")

        if (matchResult != null) {
            val (_, city, state) = matchResult.groups
            println("City: $city")
            println("State: $state")
            println("State: $state")
        } else {
            println("State and city not found")
        }*/


        /*val result = splitAddress(address)
        if (result != null) {
            val (city, state) = result
          //  println("City: $city, State: $state")
        } else {
            println("Invalid address format")
        }*/

        binding.dutyStartTv.setOnClickListener {
            /*  val fragment: Fragment
              fragment = DutyStartFragment()
              loadFragment(fragment)*/
            dutyStart()
            observerDutyData()
        }
        binding.MydutyRl.setOnClickListener{
            val fragment: Fragment
            /*val bundle = Bundle()
            bundle.putString("key", it.jTripData)*/
            fragment = DutyStartFragment()
            //fragment.arguments = bundle
            loadFragment(fragment)
        }

        binding.MytripRl.setOnClickListener{
            val fragment: Fragment
            fragment = MyTripFragment()
            loadFragment(fragment)
         //   return@setOnNavigationItemSelectedListener true
        }
        binding.PaymentRl.setOnClickListener {
            val fragment: Fragment
            fragment = PaymentFragment()
            loadFragment(fragment)
        }
        binding.DocumentRl.setOnClickListener {
            val fragment: Fragment
            fragment = DocumentFragment()
            loadFragment(fragment)
        }
        binding.settlementRl.setOnClickListener{
            val fragment: Fragment
            fragment = SettleMentFragment()
            loadFragment(fragment)
        }
        binding.ChallanRl.setOnClickListener{
            val fragment: Fragment
            fragment = ChallanFragment()
            loadFragment(fragment)
        }



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
    }


    private fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction = requireFragmentManager().beginTransaction()
        transaction.replace(R.id.content_frame, fragment)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                transaction.addToBackStack("Fragment");
        transaction.commit()
    }


    @Synchronized
    protected fun buildGoogleApiClient() {
        mGoogleApiClient = GoogleApiClient.Builder(requireContext())
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API).build()
        mGoogleApiClient!!.connect()
    }

    override fun onConnected(p0: Bundle?) {
        mLocationRequest = LocationRequest()
        mLocationRequest.interval = 1000
        mLocationRequest.fastestInterval = 1000
        mLocationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mFusedLocationClient =
                LocationServices.getFusedLocationProviderClient(requireActivity())
            mFusedLocationClient?.requestLocationUpdates(
                mLocationRequest,
                mLocationCallback,
                Looper.myLooper()
            )
        }
    }

    override fun onConnectionSuspended(p0: Int) {
        Toast.makeText(requireContext(), "connection suspended", Toast.LENGTH_SHORT).show()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Toast.makeText(requireContext(), "connection failed", Toast.LENGTH_SHORT).show()
    }

    override fun onLocationChanged(location: Location) {
        mLastLocation = location
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker!!.remove()
        }
        //Place current location marker
        val latLng = LatLng(location.latitude, location.longitude)
        lattitude = location.latitude.toString()
        longitude = location.longitude.toString()
        val markerOptions = MarkerOptions()
        markerOptions.position(latLng)
        markerOptions.title("Current Position")
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        mCurrLocationMarker = mMap!!.addMarker(markerOptions)

        //move map camera
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        mMap!!.animateCamera(CameraUpdateFactory.zoomTo(11f))
        //stop location updates
        if (mGoogleApiClient != null) {
            mFusedLocationClient?.removeLocationUpdates(mLocationCallback)
        }
    }


    //-----------------------------------Ritika image uploading code ----------------------------------------\\
    private fun getPopUpWindowFront() {
        val layoutInflater =
            requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val customView: View =
            Objects.requireNonNull(layoutInflater).inflate(R.layout.screen_popup, null)
        val capture_icon = customView.findViewById<ImageView>(R.id.capture_icon)
        val rl_custom_layout_frontscreen =
            customView.findViewById<LinearLayout>(R.id.rl_custom_layout_frontscreen)

        //instantiate popup window
        popupWindowFrontPod = PopupWindow(
            customView,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        //display the popup window
        popupWindowFrontPod.showAtLocation(rl_custom_layout_frontscreen, Gravity.CENTER, 0, 0)

        //close the popup window on button click
        capture_icon.setOnClickListener { v: View? ->
            currentType = "1"
            // selectImage()
            popupWindowFrontPod.dismiss()
        }
    }

    @SuppressLint("QueryPermissionsNeeded")


    private fun displayMessage(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun selectImage() {
        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("UPLOAD POD")
        builder.setItems(options) { dialog, item ->
            when {
                options[item] == "Take Photo" -> {
                    dispatchTakePictureIntent()
                }

                options[item] == "Cancel" -> {
                    dialog.dismiss()
                }
            }
        }
        builder.show()
    }

    private fun openAlbum() {
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = "image/jpg"
        startActivityForResult(intent, FINAL_CHOOSE_PHOTO)
    }

    fun onClickPhoto() {
        checkAndroidVersion()
    }

    private fun checkAndroidVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission()
        } else {
            // write your logic here
            // selectImage()
        }
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

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    photoURI = FileProvider.getUriForFile(
                        requireContext(),
                        "com.example.fleetech.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, FINAL_TAKE_PHOTO)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
//        val storageDir = File(
//            Environment.getExternalStorageDirectory().toString(),
//            Environment.DIRECTORY_PICTURES
//        )


        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        if (storageDir != null) {
            storageDir.mkdirs()
        }; // make sure you call mkdirs() and not mkdir()

        return File.createTempFile(
            "PNG_${timeStamp}_", /* prefix */
            ".png", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
            Log.e("our file", "our file" + absolutePath);


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            if (requestCode == FINAL_TAKE_PHOTO) {

                if (currentPhotoPath != null && currentPhotoPath != "") {
                    mBitmapValue = BitmapFactory.decodeFile(currentPhotoPath)
//                    mBitmapValue = rotateImageIfRequired(
//                        this,
//                        mBitmapValue, photoURI
//                    )
//                    mBinding.profilePic.setImageBitmap(mBitmapValue?.let { compressBitmap(it, 50) })
                    //encode image to base64 string

                    //encode image to base64 string
                    val baos = ByteArrayOutputStream()
                    mBitmapValue.compress(Bitmap.CompressFormat.JPEG, 50, baos)
                    val imageBytes = baos.toByteArray()
                    val imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT)
                    //   encodeImage(mBitmapValue)
                    imageViewFlag = true
//                    uploadLRIMAGE(currentPhotoPath)
                    //  uploadImage(imageString)
                    //      data(currentPhotoPath)
                }

            } else if (requestCode == FINAL_CHOOSE_PHOTO) {

                val uri = data?.data
//                var mBitmap = uri?.let { getBitmap(this, it) }
                val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    ImageDecoder.decodeBitmap(
                        ImageDecoder.createSource(
                            requireContext().contentResolver,
                            uri!!
                        )
                    )
                } else {
                    MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
                }
                mBitmapValue = bitmap!!
                //encode image to base64 string
                val baos = ByteArrayOutputStream()
                mBitmapValue.compress(Bitmap.CompressFormat.JPEG, 50, baos)
                val imageBytes = baos.toByteArray()
                val imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT)
//                mBinding.profilePic.setImageBitmap(mBitmapValue?.let { compressBitmap(it, 50) })
                imageGalleryWorks(uri!!)
//                uploadLRIMAGE(currentPhotoPath)
                // uploadImage(imageString)

//                data(currentPhotoPath)

//                imageViewFlag = true
            }
        }
    }

    private fun encodeImage(bm: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 20, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    private fun imageGalleryWorks(fileUri: Uri) {

        val parcelFileDescriptor =
            requireActivity().contentResolver.openFileDescriptor(fileUri, "r", null)

        parcelFileDescriptor?.let {
            val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
            destination = File(requireActivity().externalCacheDir, getFileName(fileUri))
            val outputStream = FileOutputStream(destination)

            inputStream.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }
        }

        currentPhotoPath = destination.absolutePath.toString()
    }

    /**
     * get photo uri when user take a photo from camera
     * @param uri
     * @return
     */
    private fun getFileName(fileUri: Uri): String {

        var name = ""
        val returnCursor = requireActivity().contentResolver.query(fileUri, null, null, null, null)
        if (returnCursor != null) {
            val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            returnCursor.moveToFirst()
            name = returnCursor.getString(nameIndex)
            returnCursor.close()
        }

        return name
    }

    private fun getPopUpWindowBack() {
        currentType = "2"
        val layoutInflater =
            requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val customView: View =
            Objects.requireNonNull(layoutInflater).inflate(R.layout.screen_popup_two, null)
        val capture_icon = customView.findViewById<ImageView>(R.id.capture_icon_back)
        val rl_custom_layout_backscreen =
            customView.findViewById<LinearLayout>(R.id.rl_custom_layout_backscreen)

        //instantiate popup window
        popupWindowBackPod = PopupWindow(
            customView,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        //display the popup window
        popupWindowBackPod.showAtLocation(rl_custom_layout_backscreen, Gravity.CENTER, 0, 0)

        //close the popup window on button click
        capture_icon.setOnClickListener { v: View? ->
            // selectImage()
        }
        popupWindowBackPod.dismiss()
    }


    private fun getMainImagePopUpWindow() {
        val layoutInflater =
            requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val customView: View =
            Objects.requireNonNull(layoutInflater).inflate(R.layout.pod_image_layout, null)
        val rl_pod_image_layout =
            customView.findViewById<RelativeLayout>(R.id.rl_pod_image_layout)

        //instantiate popup window
        popupWindowMainPOD = PopupWindow(
            customView,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        //display the popup window
        popupWindowMainPOD.showAtLocation(rl_pod_image_layout, Gravity.CENTER, 0, 0)
        val img_front = customView.findViewById<ImageView>(R.id.img_frontImage)
        val img_back = customView.findViewById<ImageView>(R.id.img_frontback)
        val txt_nextPod = customView.findViewById<TextView>(R.id.nextPod)
        val txt_backPod = customView.findViewById<TextView>(R.id.back_pod)
//
//        Glide.with(requireActivity()).load(mBitmapValueFront?.let { compressBitmap(it, 50) })
//            .into(img_front)
        val byteArrayOutputStream = ByteArrayOutputStream()
        mBitmapValueFront.compress(Bitmap.CompressFormat.PNG, 20, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        frontPodEncoded = android.util.Base64.encodeToString(byteArray, Base64.DEFAULT)

//        Glide.with(requireActivity()).load(mBitmapValueBack?.let { compressBitmap(it, 50) })
//            .into(img_back)
        val byteArrayOutputStream2 = ByteArrayOutputStream()
        mBitmapValueBack.compress(Bitmap.CompressFormat.PNG, 20, byteArrayOutputStream)
        val byteArray2 = byteArrayOutputStream.toByteArray()
        backPodEncoded = android.util.Base64.encodeToString(byteArray2, Base64.DEFAULT)
        println("VVVVV $backPodEncoded")

        txt_backPod.setOnClickListener { view: View? ->
            getPopUpWindowFront()
            //  initiatePopupWindowfront();
            popupWindowMainPOD.dismiss()
        }

        //uploadpods
        txt_nextPod.setOnClickListener { view: View? ->
            //uploadPOD(frontPodEncoded, backPodEncoded)
            Toast.makeText(context, "test", Toast.LENGTH_SHORT).show()
        }
        observerPODData()

    }

    override fun sendRequest(value: String?, verificationCode: String?) {

    }
    fun checkInDriver(){
     //   viewModel.CheckInDriver(sessionManager.keyToken,sessionManager.key )
    }

    fun dutyStart() {

        viewModel.DutyDriver(
            sessionManager.keyToken,
            sessionManager.mobile,
            sessionManager.keyLattitude,
            sessionManager.keyLongitude,
            address
        )
    }


    fun tripDetailData() {
        viewModel.getDriverTripDetails(
            sessionManager.keyToken
        )
    }

    fun reportedVehicleFlag() {
        viewModel.checkReportedFlag(
            sessionManager.keyToken, sessionManager.keyOrderId
        )
    }


    fun observerData() {
        viewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressBarpodstatus.visibility = View.VISIBLE
            } else {
                binding.progressBarpodstatus.visibility = View.GONE

            }
        })
        viewModel.tripList.observe(viewLifecycleOwner, Observer {
            Log.d("Json data Home", "trip_data" + it.jTripData)
            if (it.success) {
                TripStatus = it.jTripData.get(0).TripStatus
                FleetLocation = it.jTripData.get(0).FleetLocation
                binding.vehicleStatus.text = it.jTripData.get(0).TripStatus
                Log.i("TAG", "status_trip" + it.jTripData.get(0).TripStatus + " : " + it.jTripData.get(0).MultiRequestFlag)
                if (it.jTripData.get(0).TripStatus.equals("Assign") && it.jTripData.get(0).MultiRequestFlag.equals("Y")) {
                    sessionManager.keyPodtype = "ASSIGNTYPE"
                    binding.podUpload.visibility = View.VISIBLE

                    //show assign list
                } else if (it.jTripData.get(0).TripStatus.equals("EnRoute") && it.jTripData.get(0).MultiRequestFlag.equals(
                        "Y")) {
                    binding.podUpload.visibility = View.VISIBLE
                    //show dispatch list
                    sessionManager.keyPodtype = "DISPATCHTYPE"

                } else {
                    binding.podUpload.visibility = View.VISIBLE

                }
                binding.tvLoadingaddress.text = it.jTripData.get(0).Address
                binding.gpsLocationTv.text = it.jTripData.get(0).FleetLocation
                Log.i("Data POD", "Data POD" + it.jTripData)
                if (it.jTripData.get(0).PODFlag.equals("Y")) {
                    binding.podUpload.visibility = View.VISIBLE
                    sessionManager.keyPodtype = "PODTYPE"

                }
                if (it.jTripData.get(0).LoadingSlipFlag.equals("Y")) {
                    binding.podUpload.isEnabled = true
                    binding.podUpload.visibility = View.VISIBLE
                    sessionManager.keyPodtype = "LRTYPE"

                }
                sessionManager.setKeyOrderId(it.jTripData.get(0).OrderID.toString())

                if (!it.jTripData.get(0).TripSubStatus.isEmpty()) {
                    binding.vehicleSubStatus.text = it.jTripData.get(0).TripSubStatus
                } else {
                    binding.vehicleSubStatus.visibility = View.GONE
                }
                binding.accountBalance.text = "₹ " + it.jWalletBalance
                  //commented by yogesh
/*                if (it.jTripData.get(0).WalletBalColor.equals("G")) {
                    binding.walletLayout.setBackgroundResource(R.drawable.green_rounded)
                    binding.walletImg.setImageResource(R.drawable.ic_baseline_account_balance_wallet_24)
                    binding.accountBalance.setTextColor(resources.getColor(R.color.darkgreen))
                } else {
                    binding.walletLayout.setBackgroundResource(R.drawable.red_rounded)
                    binding.walletImg.setImageResource(R.drawable.ic_baseline_red_account_balance_wallet_24)
                    binding.accountBalance.setTextColor(resources.getColor(R.color.red_negative_progress))


                }*/

                if (it.jTripData.get(0).ReportedFlag.equals("Y")) {
                    sessionManager.keyPodtype = "REPORTEDTYPE"
                    binding.reportedFlag.visibility = View.VISIBLE
                    binding.reportedFlag.isEnabled = true
                    binding.podUpload.visibility = View.GONE

                }
                val vehicleNo: String = it.jTripData.get(0).VehicleNo
                val vehicleCategory: String = it.jTripData.get(0).VehicleCategory
                binding.vehicleNo.text = vehicleNo
                if (it.jTripData.get(0).LockFlag.equals("G")) {
                    binding.unlockIv.setImageResource(R.drawable.ic_baseline_lock_open_24)
                } else if (it.jTripData.get(0).LockFlag.equals("R")) {
                    binding.unlockIv.setImageResource(R.drawable.ic_baseline_lock_24)
                }
                binding.vehicleCategory.text = vehicleCategory
                binding.dispatchDateTv.text =
                    "${it.jTripData.get(0).DispatchDate} ${"-"} ${it.jTripData.get(0).DispatchTime}"
                binding.expectedarrivaldate.text =
                    "${it.jTripData.get(0).DeliveryDate} ${"-"} ${it.jTripData.get(0).DeliveryTime}"

                binding.tvLoadingaddressText.text = it.jTripData.get(0).AddressType
                binding.orderId.text = "Order ID- ${it.jTripData.get(0).OrderID.toString()}"
                var sourceAddre = it.jTripData.get(0).Source
                var destinationAddre = it.jTripData.get(0).Destination
                binding.tvTofrom.text = "${sourceAddre} ${"-"}${destinationAddre}"
                binding.fleetStatus.text = it.jTripData.get(0).FleetStatus
                sessionManager.keyWalletBalance = it.jWalletBalance

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


            } else {
                Toast.makeText(requireActivity(), "Something went wrong!!", Toast.LENGTH_SHORT)
                    .show()

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


    fun observerDutyData() {
        viewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressBarpodstatus.visibility = View.VISIBLE
            } else {
                binding.progressBarpodstatus.visibility = View.GONE

            }
        })
        viewModel.dutyDataList.observe(viewLifecycleOwner, Observer {
            if (it.success) {
                Log.d("Json data Home", "duty_start_homepage" + it.jData)
               // binding.lastcheckinLocationll
               binding.lastcheckInTv.text =   it.jData.get(0).CheckInDate
                binding.locationtv.text =   it.jData.get(0).Location
                sessionManager.setKeyInTime(it.jData.get(0).CheckInDate)
                sessionManager.setKeyLocation(it.jData.get(0).Location)


              //  Toast.makeText(requireActivity(), it.jData, Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(requireActivity(), "Something went wrong!!", Toast.LENGTH_SHORT)
                    .show()

            }

        })


    }

    fun observerPODData() {
        viewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressBarpodstatus.visibility = View.VISIBLE
            } else {
                binding.progressBarpodstatus.visibility = View.GONE

            }
        })
        viewModel.uploadPODDoc.observe(viewLifecycleOwner, Observer {
            if (it.success) {
                Toast.makeText(requireActivity(), it.jDoc, Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(requireActivity(), "Something went wrong!!", Toast.LENGTH_SHORT)
                    .show()

            }

        })


    }

    fun observerDataBalance() {
        viewModel.accountBalanceData.observe(viewLifecycleOwner, Observer {
            Log.d("Json data", "" + it.jWalletBalance)
            if (it.success) {
                sessionManager.keyWalletBalance = it.jWalletBalance
            } else {
                Toast.makeText(requireActivity(), "Something went wrong!!", Toast.LENGTH_SHORT)
                    .show()

            }

        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                buildGoogleApiClient()
                mMap!!.isMyLocationEnabled = true
                val boundsIndia =
                    LatLngBounds(LatLng(23.63936, 68.14712), LatLng(28.20453, 97.34466))
                val padding = 0 // offset from edges of the map in pixels

                val cameraUpdate = CameraUpdateFactory.newLatLngBounds(boundsIndia, padding)
                mMap!!.animateCamera(cameraUpdate)
            }
        } else {
            buildGoogleApiClient()
            mMap!!.isMyLocationEnabled = true
        }
    }

    fun splitAddress(address: String): Pair<String, String>? {


        val trimmedAddress = address.trim()

        // Split the address by comma
        val parts = trimmedAddress.split(",")

        // If there are at least two parts (city and state), extract them
        if (parts.size >= 2) {
            val city = parts[0].trim() // First part is usually city
            val state = parts[1].trim() // Second part is usually state
            return Pair(city, state)
        }

        // If unable to split or less than two parts found, return null
        return null
    }

    override fun onResume() {
        super.onResume()
        binding.lastcheckInTv.text =   sessionManager.keyInTime
        binding.locationtv.text =   sessionManager.keyLocation

    }


    fun getAddressDetails(context: Context, addressString: String): Pair<String?, String?> {
        val geocoder = Geocoder(context)
        var city: String? = null
        var state: String? = null
        try {
            val addressList: List<Address> = geocoder.getFromLocationName(addressString, 1)

            if (addressList.isNotEmpty()) {
                val address = addressList[0]
                city = address.locality
                state = address.adminArea
                println("Address list size: ${address}")

            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return Pair(city, state)
    }


}
