package com.example.fleetech.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.Notification
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.net.Uri.fromParts
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.example.fleetech.R
import com.example.fleetech.activities.ui.*
import com.example.fleetech.activities.ui.home.HomeFragment
import com.example.fleetech.databinding.ActivityMain3Binding
import com.example.fleetech.util.DrawerListAdapter
import com.example.fleetech.util.Session
import com.example.fleetech.viewModel.MainViewModel
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*


class MainActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var lastLocation: Location? = null
    private var latitudeLabel: String? = null
    private var longitudeLabel: String? = null
    private var latitudeText: TextView? = null
    private var longitudeText: TextView? = null
    private lateinit var toolbar: Toolbar
    private lateinit var navDrawer: DrawerLayout
    private lateinit var toolbar_nav_icon: Button
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMain3Binding
          //  ActivityMain3Bhinding
    lateinit var viewModel: MainViewModel
    lateinit var sessionManager: Session
    private lateinit var content: CoordinatorLayout
    private lateinit var OrderID:String
    lateinit var LRNO:String
    lateinit var Source:String
    lateinit var Destination:String
    lateinit var TripStatus:String
    lateinit var ConAddress:String
    lateinit var ContactPerson:String
    lateinit var ContactMobile:String
    lateinit var FleetLocation:String
    lateinit var FleetStatus:String
    lateinit var AddressType:String


    private val drawer_icons = intArrayOf(
        R.drawable.ic_baseline_person_24,
        R.drawable.ic_baseline_file_copy_24,
        R.drawable.ic_baseline_local_shipping_24,
        R.drawable.ic_baseline_payment_24,
        R.drawable.ic_baseline_payments_24,

    )

    private var navigation_items: java.util.ArrayList<String>? = null
    private var lv_drawer: ListView? = null


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setContentView(binding.root)
        sessionManager = Session(applicationContext)
        initView()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        tripDetailData()
//        checkAccountBalance()
        observerData()
//        observerDataBalance()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
    @RequiresApi(Build.VERSION_CODES.O)
    public override fun onStart() {
        super.onStart()
        if (!checkPermissions()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions()
            }
        } else {
            getLastLocation()
        }

    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        if(fusedLocationClient?.lastLocation!=null) {
       fusedLocationClient?.lastLocation!!.addOnCompleteListener(this) { task ->
                if (task.isSuccessful && task.result != null) {
                    lastLocation = task.result
                    showMessage(latitudeLabel + ": " + (lastLocation)!!.latitude )

                    val lattitude = (lastLocation)!!.latitude.toString()
                    val longitude = (lastLocation)!!.longitude.toString()

                    sessionManager.setLattitude(lattitude)
                    sessionManager.setKeyLongitude(longitude)
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
////                        Handler(Looper.getMainLooper()).postDelayed({
////                            /* Create an Intent that will start the Menu-Activity. */
//                            startForegroundService(Intent(applicationContext, MyService::class.java))
////
////                        }, 600000)
////
//                    } else {
////                        Handler(Looper.getMainLooper()).postDelayed({
////                            /* Create an Intent that will start the Menu-Activity. */
//                            startService(Intent(applicationContext, MyService::class.java))
////
////                        }, 600000)
////
//                  }
//                    latitudeText!!.text = latitudeLabel + ": " + (lastLocation)!!.latitude
//                    longitudeText!!.text = longitudeLabel + ": " + (lastLocation)!!.longitude
                } else {
                    Log.w(TAG, "getLastLocation:exception", task.exception)
                    showMessage("No location detected. Make sure location is enabled on the device.")
                }
            }
        }
    }
    private fun showMessage(string: String) {

//        val container = findViewById<View>(R.id.linearLayout)
//        if (container != null) {
           // Toast.makeText(this@MainActivity, string, Toast.LENGTH_LONG).show()
//        }
    }
    private fun showSnackbar(
        mainTextStringId: String, actionStringId: String,
        listener: View.OnClickListener
    ) {
        Toast.makeText(this@MainActivity, mainTextStringId, Toast.LENGTH_LONG).show()
    }
    private fun checkPermissions(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        return permissionState == PackageManager.PERMISSION_GRANTED
    }
    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(
            this@MainActivity,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            REQUEST_PERMISSIONS_REQUEST_CODE
        )
    }
    private fun requestPermissions() {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.")
            showSnackbar("Location permission is needed for core functionality", "Okay",
                View.OnClickListener {
                    startLocationPermissionRequest()
                })
        }
        else {
            Log.i(TAG, "Requesting permission")
            startLocationPermissionRequest()
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.i(TAG, "onRequestPermissionResult")
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            when {
                grantResults.isEmpty() -> {
                    // If user interaction was interrupted, the permission request is cancelled and you
                    // receive empty arrays.
                    Log.i(TAG, "User interaction was cancelled.")
                }
                grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                    // Permission granted.
                    getLastLocation()
                }
                else -> {
                    showSnackbar("Permission was denied", "Settings",
                        View.OnClickListener {
                            // Build intent that displays the App settings screen.
                            val intent = Intent()
                            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            val uri = fromParts(
                                "package",
                                Build.DISPLAY, null
                            )
                            intent.data = uri
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }
                    )
                }
            }
        }
    }
    companion object {
        private val TAG = "LocationProvider"
        private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

//    override fun onMapReady(p0: GoogleMap?) {
//    }

    override fun onConnected(p0: Bundle?) {
    }

    override fun onConnectionSuspended(p0: Int) {
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
    }

    override fun onLocationChanged(p0: Location) {

    }

    fun tripDetailData() {
        viewModel.getDriverTripDetails(
            sessionManager.keyToken
        )
    }

//    fun checkAccountBalance() {
//        viewModel.checkAccountBalance(
//            sessionManager.keyToken
//        )
//    }

    fun observerData() {
        viewModel.tripList.observe(this, Observer {
            Log.d("Json data", "" + it.jTripData)
            if (it.success) {
                sessionManager.keyTripStatus = it.jTripData.get(0).TripStatus
                sessionManager.keyDriverAddress = it.jTripData.get(0).FleetLocation
            } else {
                Toast.makeText(this, "Something went wrong!!", Toast.LENGTH_SHORT).show()

            }

        })


    }

//    fun observerDataBalance() {
//        viewModel.accountBalanceData.observe(this, Observer {
//            Log.d("Json data", "" + it.jWalletBalance)
//            if (it.success) {
//
////                val intent = Intent(this, MainActivity::class.java)
////                startActivity(intent)
//                sessionManager.keyWalletBalance = it.jWalletBalance
//                println("Check Balance ${sessionManager.keyWalletBalance}")
//                println("Check Balance 2 ${it.jWalletBalance}")
//
//
//            } else {
//                Toast.makeText(this, "Something went wrong!!", Toast.LENGTH_SHORT).show()
//
//            }
//
//        })
//    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_home -> {
                    val fragment = HomeFragment()
                    supportFragmentManager.beginTransaction().replace(
                        R.id.nav_host_fragment_content_main,
                        fragment,
                        fragment.javaClass.getSimpleName()
                    )
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.menu_my_trips -> {
                    val fragment = MyTripFragment()
                    supportFragmentManager.beginTransaction().replace(
                        R.id.nav_host_fragment_content_main,
                        fragment,
                        fragment.javaClass.getSimpleName()
                    )
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.payment_menu -> {
                    val fragment = PaymentFragment()
                    supportFragmentManager.beginTransaction().replace(
                        R.id.nav_host_fragment_content_main,
                        fragment,
                        fragment.javaClass.getSimpleName()
                    )
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }

                R.id.doc_menu -> {
                    val fragment = DocumentFragment()
                    supportFragmentManager.beginTransaction().replace(
                        R.id.nav_host_fragment_content_main,
                        fragment,
                        fragment.javaClass.getSimpleName()
                    )
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initView() {
        navigation_items = ArrayList<String>()
        setLefrDrawer()
        content = findViewById(R.id.mainView)
        lv_drawer = findViewById(R.id.drawer_list)
        toolbar = findViewById(R.id.toolbar)
        val user_name: TextView = findViewById(R.id.user_name)
//        user_name.setText(EmployeeName)
        toolbar.setTitle(null)
        setSupportActionBar(toolbar)
        navDrawer = findViewById(R.id.drawer_layout)
        toolbar_nav_icon = findViewById(R.id.nav_icon)
        //    EditText searchEt = findViewById(R.id.etSearch);
        // searchEt.setVisibility(View.INVISIBLE);
//        toolbar_destination.setVisibility(View.INVISIBLE);
        loadFragment(HomeFragment())
        //        NavigationView navigationView = findViewById(R.id.nav_view);
        SetDrawer()
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.BottomNavigation)
        bottomNavigationView.menu.getItem(0).isChecked = true
//        val ralewayBlack = Typeface.createFromAsset(assets, "Raleway-Bold.ttf")
        val signout: TextView = findViewById(R.id.txtLogOut)
        val companyName: TextView = findViewById(R.id.companyName)
        val userName: TextView = findViewById(R.id.userName)
        userName.text= sessionManager.keyDriverName
        signout.setOnClickListener {
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            finish()
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
//            finish()
//            System.exit(0)
//            val i = Intent(this, RegisterActivity::class.java)
//            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            startActivity(i)
//            finish()
        }

//        signout.setTypeface(ralewayBlack)
//        (TextView)findViewById(R.id.txtLogOut) { view ->
////                android.os.Process.killProcess(android.os.Process.myPid());
////                //initiatePopupWindowfront();
////                moveTaskToBack(true);
////                android.os.Process.killProcess(android.os.Process.myPid());
////                System.exit(1);com/e/fratebookingapp/sync/activity/HomeActivity.java:208
//            sessionManager.logoutUser()
//        }

        //------------------------------------------------------------------------------------------------------------\\
        toolbar_nav_icon.setOnClickListener(View.OnClickListener { v: View? ->
            if (navDrawer.isDrawerOpen(GravityCompat.START)) {
                navDrawer.closeDrawer(Gravity.LEFT)
            } else {
                navDrawer.openDrawer(Gravity.LEFT)
            }
        })
        bottomNavigationView.setOnNavigationItemSelectedListener { item: MenuItem ->
            val fragment: Fragment
            when (item.itemId) {
                R.id.menu_home -> {
                    fragment = HomeFragment()
                    loadFragment(fragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_my_trips -> {
                   // fragment = MyTripFragment()
                    fragment = ProfileFragment()
                    loadFragment(fragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.payment_menu -> {
                   // fragment = PaymentFragment() Notifcation fragment calling
                  //  loadFragment(fragment)
                    Toast.makeText(this@MainActivity, "In Progress", Toast.LENGTH_LONG).show()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.doc_menu -> {

                    /*fragment = DocumentFragment()
                    loadFragment(fragment)*/
                   // sessionManager.logoutUser()
                    val intent = Intent(this@MainActivity, LoginActivity::class.java)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    finish()
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)

                    finish()
                    System.exit(0)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            true
        }
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.M)
    private fun SetDrawer() {
        val toggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            navDrawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ) {
            private val scaleFactor = 100f
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                val slideX = drawerView.width * slideOffset
                content.setTranslationX(slideX)
                content.setScaleX(1 - slideOffset / scaleFactor)
                content.setScaleY(1 - slideOffset / scaleFactor)
            }
        }
        navDrawer.setScrimColor(resources.getColor(R.color.black_transparent))
        navDrawer.setDrawerElevation(0f)
        navDrawer.addDrawerListener(toggle)
        toggle.syncState()
        toolbar_nav_icon.setVisibility(View.GONE)
        lv_drawer!!.adapter = DrawerListAdapter(
            this@MainActivity,
            navigation_items,
            drawer_icons
        )
        lv_drawer!!.choiceMode = ListView.CHOICE_MODE_SINGLE
        lv_drawer!!.onItemClickListener =
            OnItemClickListener { parent: AdapterView<*>, view: View, position: Int, id: Long ->
                for (j in 0 until parent.childCount) parent.getChildAt(j)
                    .setBackgroundColor(getColor(R.color.transparent))
                // change the background color of the selected element
//                view.setBackgroundColor(getColor(R.color.yellow))
                if (navigation_items!![position].equals("Profile", ignoreCase = true)) {
                    navDrawer.closeDrawer(GravityCompat.START)
                    var fragment: Fragment? = null
                    val bundle = Bundle()
                    fragment = ProfileFragment()
                    fragment!!.arguments = bundle
                    loadFragment(fragment)
                } else if (navigation_items!![position].equals("Your Trips", ignoreCase = true)) {
                    navDrawer.closeDrawer(GravityCompat.START)
                    loadFragment(MyTripFragment())

                } else if (navigation_items!![position].equals("Incentive", ignoreCase = true)) {
                    navDrawer.closeDrawer(GravityCompat.START)
                    loadFragment(IncentiveFragment())

                } else if (navigation_items!![position].equals("Payments", ignoreCase = true)) {
                    navDrawer.closeDrawer(GravityCompat.START)
                    loadFragment(PaymentFragment())

                }
                else if (navigation_items!![position].equals("Sign Out", ignoreCase = true)) {
                    navDrawer.closeDrawer(GravityCompat.START)
                    sessionManager.logoutUser()
                    finish()
                    System.exit(0)
//                    loadFragment(PaymentFragment())

                }
                else if (navigation_items!![position].equals(
                        "Document",
                        ignoreCase = true
                    )) {
                    navDrawer.closeDrawer(GravityCompat.START)
                    loadFragment(DocumentFragment())

                }
            }
        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount > 0) {
                toggle.isDrawerIndicatorEnabled = false
                Objects.requireNonNull(supportActionBar)
                    ?.setDisplayHomeAsUpEnabled(true) // show back button
                toolbar.setNavigationOnClickListener(View.OnClickListener { v: View? -> onBackPressed() })
            } else {
                //show hamburger
                toggle.isDrawerIndicatorEnabled = true
                Objects.requireNonNull(supportActionBar)
                    ?.setDisplayHomeAsUpEnabled(false)
                toggle.syncState()
                toolbar.setNavigationOnClickListener(View.OnClickListener { v: View? ->
                    navDrawer.openDrawer(
                        GravityCompat.START
                    )
                })
            }
        }
    }

    private fun setLefrDrawer() {

//adding menu items for naviations
        navigation_items!!.add("Profile")
        navigation_items!!.add("Document")
        navigation_items!!.add("Your Trips")
        navigation_items!!.add("Payments")
        navigation_items!!.add("Incentive")

    }

    private fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content_frame, fragment)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        //        transaction.addToBackStack("Fragment");
        transaction.commit()
    }

    override fun onBackPressed() {
        val fm = supportFragmentManager
        if (fm.backStackEntryCount > 0) {
            fm.popBackStack()
        } else {
            if (navDrawer!!.isDrawerOpen(GravityCompat.START)) {
                navDrawer!!.closeDrawer(GravityCompat.START)
            } else {
                super.onBackPressed()
            }
        }
        Log.e("popping BACKSTRACK===> ", "" + fm.backStackEntryCount)
    }

    fun uploadLrDoc(){

    }

    override fun onMapReady(p0: GoogleMap) {
        TODO("Not yet implemented")
    }
}