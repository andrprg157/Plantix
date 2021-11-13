package com.plantix.test.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.plantix.test.utils.NetworkUtils
import java.util.*
import kotlin.concurrent.schedule

class CityRepositories(private val context: Context)
{


  private val  citiesLiveData = MutableLiveData<Response<List<String>>>()

    val cities : LiveData<Response<List<String>>>
    get() = citiesLiveData

     fun getCities()
    {
        citiesLiveData.postValue(Response.Loading())

        if (getCityListFromDb()!=null && getCityListFromDb().size>0)
        {
            try {

                //USED DELAY SO AS TO BE ABLE TO SEE PROGRESS DIALOG!!
                Timer().schedule(1000) {
                    // citiesLiveData.postValue(getCityListFromDb())
                    citiesLiveData.postValue(Response.Success(getCityListFromDb()))
                }


            } catch (e: Exception) {
                citiesLiveData.postValue(Response.Error("Db Fetch Error!!!"))
            }
        }
        else
        {

            if(NetworkUtils.isInternetAvailable(context))
            {
                try {
                    //FETCH DATA FROM API AND INSERT IN ROOM DB
                    citiesLiveData.postValue(Response.Success(getCityListFromApi()))
                } catch (e: Exception) {
                    citiesLiveData.postValue(Response.Error("Api  Fetch Error!!!"))
                }
            }
            else
            {
                Log.d("shiv","NET NOT CONNNECTED")
               //SHOW NOT CONNECTED MESSAGE ON MAIN ACTIVITY
                citiesLiveData.postValue(Response.Error("No Internet!!!!"))

            }

        }

    }
    fun getCityListFromApi():List<String>
    {
        // WE CAN USE RETROFIT HERE FOR FETCHING REMOTE DATABASE AND INSERT IN ROOM DB FOR FUTURE ACCESS
        return getCityList()
    }
    fun getCityListFromDb():List<String>
    {
        // WE CAN FETCH LOCAL DATA FROM ROOM DB AND ACCESS FOR DISPLAY
        return getCityList()
    }

    fun getCityList():List<String>
    {
        return  listOf("mumbai","delhi","bangalore","calcutta","hyderabad","ahmedabad",
            "chennai","kolkata","surat","pune","jaipur","jucknow","kanpur","nagpur","indore","thane","bhopal")

    }

}