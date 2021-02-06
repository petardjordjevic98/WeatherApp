package com.example.weatherapp

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.time.ExperimentalTime


class MainActivity : AppCompatActivity() {


    private val client = OkHttpClient()
    private var text: String = ""
    private val url: String = "http://api.openweathermap.org/data/2.5/onecall?lat=43.32472&lon=21.90333&exclude=daily,minutely,alerts&appid=7de28d8fee452326e7a45f294d68899b"
   private var listaDana: ArrayList<WeatherData> = ArrayList(24)

    @ExperimentalTime
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);

        val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        var txt: TextView = findViewById(R.id.message_txt)
        var btn: Button = findViewById(R.id.refresh_btn)
        var image: ImageView = findViewById(R.id.id_pozadina);

        viewModel.update()
        run(url);

        val recyclerViewFriend: RecyclerView = findViewById(R.id.recyclerView)
        val weatherAdapter = WeatherAdapter(listaDana)
        recyclerViewFriend.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewFriend.adapter = weatherAdapter
        btn.setOnClickListener {
            viewModel.update()
            Toast.makeText(this, "Your weather is refreshed!", Toast.LENGTH_LONG).show()

        }

        viewModel.time.observe(this, androidx.lifecycle.Observer {

            val hours = it.get(Calendar.HOUR_OF_DAY);

            if (hours in 6..16)
                image.setImageResource(R.drawable.day)
            else
                image.setImageResource(R.drawable.night)

            var text1: String
            if (hours in 7..10)
                text1 = "Dobro jutro"
            else if (hours in 11..16)
                text1 = "Dobar dan"
            else if (hours in 17..22)
                text1 = "Dobro vece"
            else text1 = "Laku noc"

            txt.setText(text1);
        })


    }

    fun run(url: String) {

        val request = Request.Builder()
                .url(url)
                .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                val jsonData: String? = response.body()?.string();
                val gson = Gson()

                var data = gson.fromJson(jsonData, Body::class.java)
                var x: Int = 0;
                while (x < 24) {
                    var temp: String =
                            (((data.hourly[x].temp).toDouble() - 273.15) + 1.0).toInt().toString() + " °C"
                    var vreme: String = data.hourly[x].dt;
                    var sdf = java.text.SimpleDateFormat("HH")
                    var date = java.util.Date(vreme.toLong() * 1000)
                    var sati: String = sdf.format(date) + " h"
                    var icon: List<WeatherIcon> = data.hourly[x].weather;
                    listaDana.add(WeatherData(sati, temp, icon))

                    x++

                }

            }
        })
    }


}



