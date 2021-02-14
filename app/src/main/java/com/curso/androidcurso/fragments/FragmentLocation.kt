package com.curso.androidcurso.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getColor
import com.curso.androidcurso.MainActivity
import com.curso.androidcurso.R
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FragmentLocation : Fragment(), LocationListener, OnMapReadyCallback {

    private lateinit var myLocation: Location
    private lateinit var loc: TextView
    private lateinit var locationManager: LocationManager
    private lateinit var map: GoogleMap
    private lateinit var buttonReduce: Button
    private lateinit var buttonMax: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_location, container, false)

        buttonReduce = view.findViewById(R.id.buttonReduce)
        buttonMax = view.findViewById(R.id.buttonMax)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)

        loc = view!!.findViewById<TextView>(R.id.textViewLocResult)

        if (ActivityCompat.checkSelfPermission(
                activity as MainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                activity as MainActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 123)
        } else {
            getLocation()
        }
        return view
    }

    @SuppressLint("MissingPermission")
    fun getLocation() {
        locationManager = context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val criteria = Criteria()
        val provider = locationManager.getBestProvider(criteria, true)

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                10L,
                1000F,
                this
        )

        if(provider == null) {
            Snackbar.make(
                    activity!!.findViewById(android.R.id.content),
                    "Es necesario permiso de localización",
                    Snackbar.LENGTH_SHORT
            ).show()
        } else {
            myLocation = locationManager.getLastKnownLocation(provider!!)!!

            if(myLocation != null) {
                loc.text = "${myLocation?.latitude.toString()} ${myLocation?.longitude.toString()}"
            } else {
                Snackbar.make(
                        activity!!.findViewById(android.R.id.content),
                        "Es necesario permiso de localización",
                        Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 123) {
            if(grantResults.isNotEmpty()) {
                getLocation()
            } else {
                val snackbar = Snackbar.make(view!!, "Permissions are needed", Snackbar.LENGTH_SHORT).setAction("Action",null)
                snackbar.setActionTextColor(Color.BLUE)
                snackbar.show()
            }
        }
    }

    override fun onLocationChanged(location: Location) {
        locationManager = context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        loc.text = "${location.latitude} ${location.longitude}"

        locationManager.removeUpdates(this)
    }

    override fun onStop() {
        super.onStop()
        locationManager = context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        locationManager.removeUpdates(this)
    }

    // GOOGLE MAPS

    override fun onMapReady(googleMap: GoogleMap) {

        map = googleMap

        map.setMinZoomPreference(5F)
        map.setMaxZoomPreference(20F)
        map.uiSettings.isZoomControlsEnabled = true

        val here = LatLng(myLocation.latitude, myLocation.longitude)

        map.addMarker(MarkerOptions().position(here).title("Current Position"))
        map.moveCamera(CameraUpdateFactory.newLatLng(here))

        val radius = 50000.0
        var newRadius = 50000.0

        val circleOptions = CircleOptions()
        circleOptions.center(here)
        circleOptions.radius(radius)
        circleOptions.fillColor(Color.RED)
        circleOptions.strokeColor(Color.RED)
        circleOptions.strokeWidth(4f)
        circleOptions.fillColor(getColor(activity!!, R.color.semiRed))

        var mapCircle: Circle = map.addCircle(circleOptions)

        buttonMax.setOnClickListener {
            if(mapCircle != null) {
                mapCircle.remove()
            }
            newRadius = newRadius + 50000.0
            circleOptions.radius(newRadius)
            circleOptions.strokeColor(Color.RED)
            circleOptions.strokeWidth(4f)
            circleOptions.fillColor(getColor(activity!!, R.color.semiRed))

            mapCircle = map.addCircle(circleOptions)
        }
        buttonReduce.setOnClickListener {
            if((newRadius - 500) >= 0 ){
                if(mapCircle != null) {
                    mapCircle.remove()
                }
                newRadius = newRadius - 50000.0
                circleOptions.radius(newRadius)
                circleOptions.strokeColor(Color.RED)
                circleOptions.strokeWidth(4f)
                circleOptions.fillColor(getColor(activity!!, R.color.semiRed))

                mapCircle = map.addCircle(circleOptions)
            }
        }


    }



    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentLocation().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}