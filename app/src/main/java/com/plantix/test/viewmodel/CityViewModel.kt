package com.shiv.rsrtck.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plantix.test.repositories.CityRepositories
import com.plantix.test.repositories.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CityViewModel(private val cityRepositories: CityRepositories): ViewModel()
{
    init {

        viewModelScope.launch(Dispatchers.IO)
        {
            cityRepositories.getCities()
        }


    }
    val cities : LiveData<Response<List<String>>>
        get() = cityRepositories.cities
}