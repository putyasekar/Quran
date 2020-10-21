package com.putya.quran.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.AsyncTask
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.putya.quran.R
import com.putya.quran.utils.ParserPlace
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class MasjidActivity : AppCompatActivity(), LocationListener {
    var mGoogleMap: GoogleMap? = null
    var toolbar: Toolbar? = null

    @SuppressLint("Assert")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_masjid)

        toolbar = findViewById(R.id.toolbar_masjid)
        setSupportActionBar(toolbar)

        assert(supportActionBar != null)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = null

        val fragment = supportFragmentManager.findFragmentById(R.id.fMap) as SupportMapFragment?
        fragment!!.getMapAsync { googleMap ->
            mGoogleMap = googleMap
            initMap()
        }
    }

    private fun initMap() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 115
            )
            return
        }

        if (mGoogleMap != null) {
            mGoogleMap!!.isMyLocationEnabled = true

            val locationManager = getSystemService(Context.LOCATION_SERVICE)
                    as LocationManager

            val criteria = Criteria()
            val provider = locationManager.getBestProvider(criteria, true)

            val location = locationManager.getLastKnownLocation(provider!!)

            if (location != null) {
                onLocationChanged(location)

            } else locationManager.requestLocationUpdates(provider, 2000, 0f, this)
        }
    }

    override fun onLocationChanged(p0: Location) {
        val mLatitude = p0.latitude
        val mLongitude = p0.longitude
        val latLng = LatLng(mLatitude, mLongitude)

        //marker
        mGoogleMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        mGoogleMap!!.animateCamera(CameraUpdateFactory.zoomTo(12f))

        //API Key Android SDK link
        val sb = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                "location=" + mLatitude + "," + mLongitude +
                "&radius=20000" +
                "&types=" + "mosque" +
                "&key=" + resources.getString(R.string.google_maps_key)

        PlacesTask().execute(sb)
    }

    @SuppressLint("StaticFieldLeak")
    private inner class PlacesTask : AsyncTask<String?, Int?, String?>() {
        override fun doInBackground(vararg p0: String?): String? {
            TODO("Not yet implemented")
        }
    }
}
