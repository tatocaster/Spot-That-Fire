package me.tatocaster.nasaappchallenge.features.map.presentation

import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.res.Resources
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import me.tatocaster.nasaappchallenge.FASTEST_INTERVAL
import me.tatocaster.nasaappchallenge.GOOGLE_LOCATION_SERVICE_REQUEST_CODE
import me.tatocaster.nasaappchallenge.INTERVAL
import me.tatocaster.nasaappchallenge.R
import me.tatocaster.nasaappchallenge.features.base.BaseActivity
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*


class MapsActivity : BaseActivity(), OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private lateinit var mMap: GoogleMap
    private lateinit var googleApiClient: GoogleApiClient
    private var currentLocation: Location? = null
    private lateinit var locationRequest: LocationRequest
    private var googleMap: GoogleMap? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var selectedMarkerLatLng: LatLng? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT < 22)
            setStatusBarTranslucent(false)
        else
            setStatusBarTranslucent(true)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation
                .addOnSuccessListener(this) { location ->
                    if (location != null) {
                        currentLocation = location
                        gotoLocation(location.latitude, location.longitude)
                    }
                }

        createLocationRequest()

        finishLocation.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("latlng", selectedMarkerLatLng)
            val returnIntent = Intent()
            returnIntent.putExtra("result", bundle)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.isMyLocationEnabled = true
        this.googleMap = googleMap
        try {
            googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
        } catch (e: Resources.NotFoundException) {
        }

        currentLocation?.let {
            gotoLocation(it.latitude, it.longitude)
        }

        mMap = googleMap

        mMap.setOnMapClickListener { latLng ->
            // Creating a marker
            val markerOptions = MarkerOptions()

            // Setting the position for the marker
            markerOptions.position(latLng)

            // Setting the title for the marker.
            // This will be displayed on taping the marker
            markerOptions.title(latLng.latitude.toString() + " : " + latLng.longitude.toString())

            // Clears the previously touched position
            googleMap.clear()

            // Animating to the touched position
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))

//            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
            mMap.addMarker(markerOptions)
            selectedMarkerLatLng = latLng

            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        }

        mMap.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
            override fun onMarkerDragStart(arg0: Marker) {}

            override fun onMarkerDragEnd(arg0: Marker) {
                mMap.animateCamera(CameraUpdateFactory.newLatLng(arg0.position))
                selectedMarkerLatLng = arg0.position
            }

            override fun onMarkerDrag(arg0: Marker) {}
        })

    }

    private fun getLocation() {
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest) { location -> currentLocation = location }
    }

    private fun gotoLocation(latitude: Double, longitude: Double) {
        googleMap?.let {
            it.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), 17f))
        }
    }

    protected fun createLocationRequest() {
        googleApiClient = GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build()
        googleApiClient.connect()

        locationRequest = LocationRequest()
        locationRequest.interval = INTERVAL
        locationRequest.fastestInterval = FASTEST_INTERVAL
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)

        val result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())

        result.setResultCallback { result1 ->
            val status = result1.status
            when (status.statusCode) {
                LocationSettingsStatusCodes.SUCCESS ->
                    // All location settings are satisfied. The client can
                    // initialize location requests here.
                    getLocation()
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        status.startResolutionForResult(this, GOOGLE_LOCATION_SERVICE_REQUEST_CODE)
                    } catch (e: IntentSender.SendIntentException) {
                        // Ignore the error.
                    }

                LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            GOOGLE_LOCATION_SERVICE_REQUEST_CODE -> when (resultCode) {
                Activity.RESULT_OK -> {
                    // All required changes were successfully made
                    getLocation()
                }
                Activity.RESULT_CANCELED -> {
                }// The user was asked to change settings, but chose not to
                else -> {
                }
            }
        }
    }

    override fun onStop() {
        googleApiClient.disconnect()
        super.onStop()
    }

    override fun onConnected(bundle: Bundle?) {

    }

    override fun onConnectionSuspended(i: Int) {

    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {

    }

    private fun setStatusBarTranslucent(makeTranslucent: Boolean) {
        if (makeTranslucent) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }
}
