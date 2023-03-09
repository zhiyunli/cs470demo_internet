package com.bc.week10demo2

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MarsViewModel(): ViewModel() {

    private val _properties = MutableLiveData<List<MarsProperty>>()
    val properties: LiveData<List<MarsProperty>>
        get() = _properties

    init{
        viewModelScope.launch {
            _properties.value = MarsApi.retrofitService.getProperties()
            Log.d("properties", _properties.value.toString())
        }
    }

}