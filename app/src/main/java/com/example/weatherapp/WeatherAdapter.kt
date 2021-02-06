package com.example.weatherapp

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class WeatherAdapter(var listaDana: ArrayList<WeatherData>) : RecyclerView.Adapter<WeatherAdapter.WeatherHolder>() {


    inner open class WeatherHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val sati = itemView.findViewById<TextView>(R.id.sati)
        val temp = itemView.findViewById<TextView>(R.id.temp)
        val image = itemView.findViewById<ImageView>(R.id.imageView);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherAdapter.WeatherHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weatherdata, null)

        return WeatherHolder(view)
    }

    override fun getItemCount(): Int {
        return listaDana.size
    }

    override fun onBindViewHolder(holder: WeatherAdapter.WeatherHolder, position: Int) {

        holder.sati.setText(listaDana[position].dt)
        holder.temp.setText(listaDana[position].temp)

        when (listaDana[position].weather[0].description) {

            "overcast clouds" -> holder.image.setImageResource(R.drawable.overcast_clouds)
            "scattered clouds" -> holder.image.setImageResource(R.drawable.scattered_clouds)
            "clear sky" -> holder.image.setImageResource(R.drawable.sunny)
            "broken clouds" -> holder.image.setImageResource(R.drawable.scattered_clouds)
            "light rain" -> holder.image.setImageResource(R.drawable.rain)
            else -> holder.image.setImageResource(R.drawable.cloudy)
        }
    }

}