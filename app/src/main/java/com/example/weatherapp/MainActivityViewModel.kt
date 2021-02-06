package com.example.weatherapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class MainActivityViewModel : ViewModel() {

    private var _time = MutableLiveData<Calendar>()
    val time: LiveData<Calendar>
        get() = _time

    fun update() {

        _time.value = Calendar.getInstance()

    }

}