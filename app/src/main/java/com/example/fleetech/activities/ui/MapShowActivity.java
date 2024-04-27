package com.example.fleetech.activities.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Path;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.fleetech.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.model.Bounds;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.EncodedPolyline;
import com.google.maps.model.TravelMode;
//import com.maps.route.model.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MapShowActivity extends AppCompatActivity implements OnMapReadyCallback {
    //public LatLng origin;
    //public LatLng destination;
    public LatLng currentlocation;
    //public String SLat = "", Slong = "", DLat = "", Dlong = "", CLat = "", Clong = "", serverKey = "";
    public LinearLayout llayoutConsignerAddressGmap;//Consignor for google map redirect
    private GoogleMap mMap;

    private LatLng origin = new LatLng(40.7128, -74.0060);  // Example: New York City
    private LatLng destination = new LatLng(34.0522, -118.2437);  // Example: Los Angeles

    private Double Dlat = 0.0 ,Dlong = 0.0 , Slat = 0.0 , Slong = 0.0;
    private boolean isZoomed = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_show);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
      /*  if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }*/

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
             Dlat = bundle.getDouble("Destionation_lat");
             Dlong = bundle.getDouble("Destionation_long");
             Slat = bundle.getDouble("Source_lat");
             Slong = bundle.getDouble("Source_long");
            Log.i("TAG","check_lat_lng" + Dlat + " : " + Slong);

            // Display the data using a Toast
        //    Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
        }
    }

   /* @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.addMarker(new MarkerOptions().position(origin).title("Origin"));
        mMap.addMarker(new MarkerOptions().position(destination).title("Destination"));

       // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, 6));

        drawRoute(origin,destination);
       // calculateDirections();
    }

    private void calculateDirections() {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyC1hLmKsVkcb92aRKncpGn0ISx6QfGXhLo")
                .build();

        DirectionsApi.newRequest(context)
                .mode(TravelMode.DRIVING)
                .origin(new com.google.maps.model.LatLng(origin.latitude, origin.longitude))
                .destination(new com.google.maps.model.LatLng(destination.latitude, destination.longitude))
                .setCallback(new PendingResult.Callback<DirectionsResult>() {
                    @Override
                    public void onResult(DirectionsResult result) {
                        if (result.routes != null && result.routes.length > 0) {
                            addPolylineToMap(result);
                        }
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }

    private void addPolylineToMap(DirectionsResult result) {
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.BLUE);

        // Decoding polyline and adding points to polylineOptions
        for (com.google.maps.model.LatLng latLng : result.routes[0].overviewPolyline.decodePath()) {
            polylineOptions.add(new LatLng(latLng.lat, latLng.lng));
        }

        runOnUiThread(() -> {
            if (mMap != null) {
                Polyline polyline = mMap.addPolyline(polylineOptions);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(origin, 6));
            }
        });
    }



    private void drawRoute(LatLng origin, LatLng destination) {
        new Thread(() -> {
            try {
                GeoApiContext geoApiContext = new GeoApiContext.Builder()
                        .apiKey("AIzaSyBt2eM1Qze4pfcRL5BlCx8JBwVCBGXcD3s")
                        .build();

                DirectionsResult result = DirectionsApi.newRequest(geoApiContext)
                        .mode(TravelMode.DRIVING)
                        .origin(new com.google.maps.model.LatLng(origin.latitude, origin.longitude))
                        .destination(new com.google.maps.model.LatLng(destination.latitude, destination.longitude))
                        .await();

                runOnUiThread(() -> {
                    if (result.routes != null && result.routes.length > 0) {
                        mMap.addPolyline(new PolylineOptions()
                                .addAll(decodePolyline(result.routes[0].overviewPolyline.getEncodedPath()))
                                .color(Color.BLUE));

                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, 10));
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private List<LatLng> decodePolyline(String encoded) {
        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((double) lat / 1E5, (double) lng / 1E5);
            poly.add(p);
        }
        return poly;
    }
}*/

        //Open google map for Navigation


      /*  SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }*/

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        LatLng barcelona = new LatLng(Slat,Slong);
        mMap.addMarker(new MarkerOptions().position(barcelona).title("Source"));

        LatLng madrid = new LatLng(Dlat,Dlong);
        mMap.addMarker(new MarkerOptions().position(madrid).title("Destination"));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(barcelona));
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);

        // googleMap.moveCamera(center);
        mMap.animateCamera(zoom);

        String sourceLat = String.valueOf(Slat);
        String sourceLong = String.valueOf(Slong);
        String desLat = String.valueOf(Dlat);
        String desLong = String.valueOf(Dlong);

        String origin = sourceLat + "," + sourceLong;
        String desttination = desLat + "," + desLong;

        LatLng zaragoza = new LatLng(Slat,Slong);

        LatLng startLocation = new LatLng(Slat, Slong);
        LatLng endLocation = new LatLng(Dlat, Dlong);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(startLocation);
        builder.include(endLocation);
        LatLngBounds bounds = builder.build();

        //  Bounds bounds = LatLngBounds.Builder().include(origin).include(desttination).build();

        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
                clearAndRedrawMap();
            }
        });
        mMap.setOnCameraMoveStartedListener(reason -> {
            if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE && !isZoomed) {
                clearAndRedrawMap();
            }
        });

        //Define list to get all latlng for the route
        List<LatLng> path = new ArrayList();


        //Execute Directions API request
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyD0lHd7Q5j0lhJSyum94EyWE62QsgZZ0vA")
                .build();
        DirectionsApiRequest req = DirectionsApi.getDirections(context, origin, desttination);
        try {
            DirectionsResult res = req.await();

            //Loop through legs and steps to get encoded polylines of each step
            if (res.routes != null && res.routes.length > 0) {
                DirectionsRoute route = res.routes[0];

                if (route.legs !=null) {
                    for(int i=0; i<route.legs.length; i++) {
                        DirectionsLeg leg = route.legs[i];
                        if (leg.steps != null) {
                            for (int j=0; j<leg.steps.length;j++){
                                DirectionsStep step = leg.steps[j];
                                if (step.steps != null && step.steps.length >0) {
                                    for (int k=0; k<step.steps.length;k++){
                                        DirectionsStep step1 = step.steps[k];
                                        EncodedPolyline points1 = step1.polyline;
                                        if (points1 != null) {
                                            //Decode polyline and add points to list of route coordinates
                                            List<com.google.maps.model.LatLng> coords1 = points1.decodePath();
                                            for (com.google.maps.model.LatLng coord1 : coords1) {
                                                path.add(new LatLng(coord1.lat, coord1.lng));
                                            }
                                        }
                                    }
                                } else {
                                    EncodedPolyline points = step.polyline;
                                    if (points != null) {
                                        //Decode polyline and add points to list of route coordinates
                                        List<com.google.maps.model.LatLng> coords = points.decodePath();
                                        for (com.google.maps.model.LatLng coord : coords) {
                                            path.add(new LatLng(coord.lat, coord.lng));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch(Exception ex) {
            Log.e("TAG", ex.getLocalizedMessage());
        }

        //Draw the polyline
        if (path.size() > 0) {
            PolylineOptions opts = new PolylineOptions().addAll(path).color(Color.BLUE).width(12);
            mMap.addPolyline(opts);
        }

        mMap.getUiSettings().setZoomControlsEnabled(true);



        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(zaragoza, 2f));
    }

    private void clearAndRedrawMap() {
        LatLng sydney = new LatLng(Slat,Slong);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(10);

        // googleMap.moveCamera(center);
        mMap.animateCamera(zoom);

       /* mMap.clear();
        LatLng sydney = new LatLng(Slat,Slong);
        mMap.addMarker(new MarkerOptions().position(sydney).title("INDIA"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));
        isZoomed = true;*/
    }
}