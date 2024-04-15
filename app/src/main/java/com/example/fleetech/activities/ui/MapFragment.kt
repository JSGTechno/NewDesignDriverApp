package com.example.fleetech.activities.ui

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.fleetech.R
import com.example.fleetech.util.Session
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.DirectionsApi
import com.google.maps.DirectionsApi.getDirections
import com.google.maps.GeoApiContext
import com.google.maps.model.DirectionsResult
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class MapFragment : Fragment() {

    private lateinit var googleMap: GoogleMap
    private lateinit var sessionManager: Session


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        sessionManager = Session(activity)
        return inflater.inflate(R.layout.fragment_map, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync { map ->
            googleMap = map*/


        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync { map ->
            googleMap = map
            val Destination_Lat = arguments?.getDouble("Destionation_lat")
            val Destionation_long = arguments?.getDouble("Destionation_long")
            val Source_lat = arguments?.getDouble("Source_lat")
            val Source_long = arguments?.getDouble("Source_long")

            val source_l: Double = Source_lat?.toDouble() ?: 0.0
            val source_lo: Double = Source_long?.toDouble() ?: 0.0
            val des_lat: Double = Destination_Lat?.toDouble() ?: 0.0
            val des_lng: Double = Destionation_long?.toDouble() ?: 0.0

            Log.i("TAG", "check_the_date" + Destination_Lat)

            // val startLocation = LatLng(source_l, source_lo) // Example start location (Delhi, India)
            val startLocation = LatLng(
                sessionManager.keyLattitude.toDouble(),
                sessionManager.keyLongitude.toDouble()
            )
            val endLocation = LatLng(des_lat, des_lng) // Example end location (New Delhi, India)

            // Call function to show start and end locations with polyline
            //  showStartAndEndLocations(startLocation, endLocation)
            // Draw the route
            /* drawRoute(startLocation, endLocation)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startLocation, 7f))
            Log.i("TAG","loc_check" + startLocation + ":" + endLocation)*/

            val firstLocation = LatLng( 40.984443, 28.7324437)
            val secondLocation = LatLng(40.9822821, 28.7210424)

            googleMap?.addMarker(MarkerOptions().position(firstLocation)
                .icon(context?.let { bitmapFromVector(it, R.drawable.map_loc) }))

            googleMap?.addMarker(MarkerOptions().position(secondLocation)
                .icon(context?.let { bitmapFromVector(it, R.drawable.map_loc) }))


            val paths: MutableList<LatLng> = ArrayList()

            val geoApiContext = GeoApiContext.Builder()
                .apiKey("AIzaSyD0lHd7Q5j0lhJSyum94EyWE62QsgZZ0vA")
                .build()

            val req = DirectionsApi.getDirections(geoApiContext,
                "${secondLocation.latitude},${secondLocation.longitude}",
                "${firstLocation.latitude},${firstLocation.longitude}")
            try {
                val res = req.await()
                if (res.routes.isNullOrEmpty().not()) {
                    val route = res.routes[0]
                    if (route.legs.isNullOrEmpty().not()) {
                        for (leg in route.legs) {
                            if (leg.steps.isNullOrEmpty().not()) {
                                for (step in leg.steps) {
                                    if (step.steps.isNullOrEmpty().not()) {
                                        for (step1 in step.steps) {
                                            step1.polyline?.let { points1 ->
                                                val coordinates = points1.decodePath()
                                                for (coordinate in coordinates) {
                                                    paths.add(LatLng(coordinate.lat, coordinate.lng))
                                                }
                                            }

                                        }
                                    } else {
                                        step.polyline?.let { points ->
                                            val coordinates = points.decodePath()
                                            for (coordinate in coordinates) {
                                                paths.add(LatLng(coordinate.lat, coordinate.lng))
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (ex: Exception) {
                Log.e("DirectionsApi", "DirectionsApi exception localizedMessage: ${ex.localizedMessage}")
            }

            if (paths.isNotEmpty()) {
                val opts = PolylineOptions().addAll(paths).color(Color.BLUE).width(5f)
                googleMap?.addPolyline(opts)
            }

            googleMap?.uiSettings?.isZoomControlsEnabled = true

        }
    }



        }

private fun bitmapFromVector(context: Context, vectorResId: Int): BitmapDescriptor {
    val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
    vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
    val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    vectorDrawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}






/*fun showStartAndEndLocations(startLocation: LatLng, endLocation: LatLng) {
    // Add markers for start and end locations
    googleMap.addMarker(MarkerOptions().position(startLocation).title("Start Location"))
    googleMap.addMarker(MarkerOptions().position(endLocation).title("End Location"))

    // Move camera to the center of start and end locations
    val bounds = LatLngBounds.Builder().include(startLocation).include(endLocation).build()
    googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))

    // Draw polyline between start and end locations
    googleMap.addPolyline(
        PolylineOptions()
        .add(startLocation, endLocation)
        .width(5f)
        .color(Color.RED))
}*/

