package com.example.weatherapp

data class Body(
        var lat: String,
        var lon: String,
        var current: WeatherData,
        var hourly: List<WeatherData>
)
