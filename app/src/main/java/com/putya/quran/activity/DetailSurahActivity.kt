package com.putya.quran.activity

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.google.android.gms.common.api.Api
import com.putya.quran.BuildConfig
import com.putya.quran.R
import com.putya.quran.adapter.AyatAdapter
import com.putya.quran.model.ModelAyat
import com.putya.quran.model.ModelSurah
import kotlinx.android.synthetic.main.activity_detail_surah.*
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException
import java.util.ArrayList

class DetailSurahActivity : AppCompatActivity() {
    var nomor: String? = null
    var nama: String? = null
    var arti: String? = null
    var type: String? = null
    var ayat: String? = null
    var keterangan: String? = null
    var audio: String? = null
    var modelSurah: ModelSurah? = null
    var ayatAdapter: AyatAdapter? = null
    var progressDialog: ProgressDialog? = null
    var modelAyat: MutableList<ModelAyat> = ArrayList()
    var mHandler: Handler? = null

    @SuppressLint("RestrictedApi", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_surah)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        toolbar_detail.title = null
        setSupportActionBar(toolbar_detail)

//        assert(supportActionBar != null) bisa jd ini

        if (BuildConfig.DEBUG && supportActionBar == null) {
            error("Assertion failed")
        }
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mHandler = Handler()

        modelSurah = intent.getSerializableExtra("Detail Surah") as ModelSurah
        if (modelSurah != null) {
            nomor = modelSurah!!.nomor
            nama = modelSurah!!.nama
            arti = modelSurah!!.arti
            type = modelSurah!!.type
            ayat = modelSurah!!.ayat
            audio = modelSurah!!.audio
            keterangan = modelSurah!!.keterangan

            fabStop.visibility = View.GONE
            fabPlay.visibility = View.VISIBLE

            tvHeader.text = nama
            tvTitle.text = nama
            tvSubTitle.text = arti
            tvInfo.text = "$type - $ayat Ayat "

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) tvKet.text = Html.fromHtml(
                keterangan,
                Html.FROM_HTML_MODE_COMPACT
            )
            else {
                tvKet.text = Html.fromHtml(keterangan)
            }

            val mediaPlayer = MediaPlayer()
            fabPlay.setOnClickListener(View.OnClickListener {
                try {
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
                    mediaPlayer.setDataSource(audio)
                    mediaPlayer.prepare()
                    mediaPlayer.start()

                } catch (e: IOException) {
                    e.printStackTrace()
                }
                fabPlay.visibility = View.GONE
                fabStop.visibility = View.VISIBLE
            })

            fabStop.setOnClickListener(View.OnClickListener {
                mediaPlayer.stop()
                mediaPlayer.reset()

                fabPlay.visibility = View.VISIBLE
                fabStop.visibility = View.GONE
            })
        }
        progressDialog = ProgressDialog(this)
        progressDialog!!.setTitle("Mohon Tunggu")
        progressDialog!!.setCancelable(false)
        progressDialog!!.setMessage("Sedang menampilkan data...")

        rvAyat.layoutManager = LinearLayoutManager(this)
        rvAyat.setHasFixedSize(true)

//        listAyat()
    }

//    private fun listAyat() {
//        progressDialog!!.show()
//
//        AndroidNetworking.get(Api.URL_API_AYAT)
//            .addPathParameter("nomor", nomor)
//            .setPriority(Priority.MEDIUM)
//            .build()
//            .getAsJSONArray(object : JSONArrayRequestListener {
//
//                override fun onResponse(response: JSONArray) {
//                    for (i in 0 until response.length()) {
//                        try {
//                            progressDialog!!.dismiss()
//                            val dataApi = ModelAyat()
//                            val jsonObject = response.getJSONObject(i)
//
//                            dataApi.nomor = jsonObject.getString("nomor")
//                            dataApi.arab = jsonObject.getString("arab")
//                            dataApi.indo = jsonObject.getString("indonesia")
//                            dataApi.terjemahan = jsonObject.getString("terjemah")
//
//                            modelAyat.add(dataApi)
//
//                            showListAyat()
//
//                        } catch (e: JSONException) {
//                            e.printStackTrace()
//
//                            Toast.makeText(
//                                this@DetailSurahActivity, "Gagal menampilkan data!",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    }
//                }
//
//                override fun onError(anError: ANError) {
//                    progressDialog!!.dismiss()
//
//                    Toast.makeText(
//                        this@DetailSurahActivity, "Tidak ada jaringan internet!",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            })
//    }

    private fun showListAyat() {
        ayatAdapter = AyatAdapter(this@DetailSurahActivity, modelAyat)
        rvAyat!!.adapter = ayatAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()

            return true
        }
        return super.onOptionsItemSelected(item)
    }
}