package com.shiv.rsrtck.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.Factory
import com.plantix.test.repositories.CityRepositories

class CityViewModelFactory(private val cityRepositories: CityRepositories): Factory
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CityViewModel(cityRepositories) as T
    }
}