package com.plantix.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plantix.test.adpter.CustomAdapter
import com.plantix.test.repositories.CityRepositories
import com.plantix.test.repositories.Response
import com.shiv.rsrtck.viewmodels.CityViewModel
import com.shiv.rsrtck.viewmodels.CityViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var cityViewModel: CityViewModel
    lateinit var progressBar: ProgressBar
    lateinit var tverror: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.progressbrcity);
        tverror = findViewById(R.id.tverror)
        val recyclrvw = findViewById<RecyclerView>(R.id.recyclervwcity)
        val mlayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclrvw.layoutManager = mlayoutManager
        val cityRepositories = CityRepositories(this)
        cityViewModel = ViewModelProvider(this,CityViewModelFactory(cityRepositories)).get(CityViewModel::class.java)
        cityViewModel.cities.observe(this, Observer {
            when (it) {
                is Response.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
                is Response.Success -> {
                    it.data?.let {
                        progressBar.visibility = View.INVISIBLE
                        tverror.visibility = View.INVISIBLE
                        recyclrvw.visibility = View.VISIBLE
                        val adapter = CustomAdapter(it)
                        recyclrvw.adapter = adapter
                        DividerItemDecoration(
                            this, // context
                            mlayoutManager.orientation
                        ).apply { recyclrvw.addItemDecoration(this) }
                    }
                }
                is Response.Error -> {
                    progressBar.visibility = View.INVISIBLE
                    tverror.visibility = View.VISIBLE
                    recyclrvw.visibility = View.INVISIBLE
                    tverror.text = it.toString()
                    Toast.makeText(this, "Error is " + it.errormessage, Toast.LENGTH_LONG).show()
                }
            }

        })
    }
}