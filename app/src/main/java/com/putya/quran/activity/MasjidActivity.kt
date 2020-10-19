package com.putya.quran.activity

import android.Manifest
import android.annotation.SuppressLint
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
//        supportActionBar!!.title(null)
        val fragment = supportFragmentManager.findFragmentById(R.id.fMap) as SupportMapFragment?
        fragment!!.getMapAsync { googleMap ->
            mGoogleMap = googleMap
            initMap()
        }
    }

    private fun initMap() {
        TODO("Not yet implemented")
    }

    override fun onLocationChanged(p0: Location) {
        TODO("Not yet implemented")
    }
}


//    private fun initMap() {
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED &&
//            ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                115
//            )
//            return
//        }
//        if (mGoogleMap != null) {
//            mGoogleMap!!.isMyLocationEnabled = true
//            val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
//            val criteria = Criteria()
//            val provider = locationManager.getBestProvider(criteria, true)
//            val location = locationManager.getLastKnownLocation(provider!!)
//            if (location != null) {
//                onLocationChanged(location)
//            } else locationManager.requestLocationUpdates(provider, 20000, 0f, this)
//        }
//    }
//
//    override fun onLocationChanged(location: Location) {
//        val mLatitude = location.latitude
//        val mLongitude = location.longitude
//        val latLng = LatLng(mLatitude, mLongitude)
//        mGoogleMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
//        mGoogleMap!!.animateCamera(CameraUpdateFactory.zoomTo(12f))
//        val sb = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
//                "location=" + mLatitude + "," + mLongitude +
//                "&radius=20000" +
//                "&types=" + "mosque" +
//                "&key=" + resources.getString(R.string.google_maps_key)
//        PlacesTask().execute(sb)
//    }
//
//    @SuppressLint("StaticFieldLeak")
//    private inner class PlacesTask : AsyncTask<String?, Int?, String?>() {
//         override fun doInBackground(vararg p0: String?): String? {
//            var data: String? = null
//            try {
//                data = downloadUrl(strUrl = [0])
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//            return data
//        }
//
//        override fun onPostExecute(result: String?) {
//            ParserTask().execute(result)
//        }
//    }
//
////    private fun downloadUrl(strUrl: Array<Int>): String {
////        var data = ""
////        val iStream: InputStream
////        val urlConnection: HttpURLConnection
////        try {
//////            val url = URL(strUrl)
//////            urlConnection = url.openConnection() as HttpURLConnection
////            urlConnection.connect()
////            iStream = urlConnection.inputStream
////            val br = BufferedReader(InputStreamReader(iStream))
////            val sb = StringBuilder()
////            var line: String?
////            while (br.readLine().also { line = it } != null) {
////                sb.append(line)
////            }
////            data = sb.toString()
////            br.close()
////            iStream.close()
////            urlConnection.disconnect()
////        } catch (e: Exception) {
////            e.printStackTrace()
////        }
////        return data
////    }
//
////    @SuppressLint("StaticFieldLeak")
////    private inner class ParserTask :
////        AsyncTask<String?, Int?, List<HashMap<String, String>>?>() {
////        var jObject: JSONObject? = null
////        protected override fun doInBackground(vararg jsonData: String): List<HashMap<String, String>>? {
////            var places: List<HashMap<String, String>>? = null
////            val parserPlace = ParserPlace()
////            try {
////                jObject = JSONObject(jsonData[0])
////                places = parserPlace.parse(jObject)
////            } catch (e: Exception) {
////                e.printStackTrace()
////            }
////            return places
////        }
//
//        override fun onPostExecute(list: List<HashMap<String, String>>?) {
//            mGoogleMap!!.clear()
//            for (i in list!!.indices) {
//                val markerOptions = MarkerOptions()
//                val hmPlace = list[i]
//                val pinDrop = BitmapDescriptorFactory.fromResource(R.drawable.ic_place)
//                val lat = hmPlace["lat"]!!.toDouble()
//                val lng = hmPlace["lng"]!!.toDouble()
//                val nama = hmPlace["place_name"]
//                val namaJln = hmPlace["vicinity"]
//                val latLng = LatLng(lat, lng)
//                markerOptions.icon(pinDrop)
//                markerOptions.position(latLng)
//                markerOptions.title("$nama : $namaJln")
//                mGoogleMap!!.addMarker(markerOptions)
//            }
//        }
//    }
//
//    override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {}
//    override fun onProviderEnabled(s: String) {}
//    override fun onProviderDisabled(s: String) {}
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.itemId == android.R.id.home) {
//            finish()
//            return true
//        }
//        return super.onOptionsItemSelected(item)
//    }
//}