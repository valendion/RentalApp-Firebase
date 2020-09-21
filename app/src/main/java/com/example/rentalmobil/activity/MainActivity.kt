package com.example.rentalmobil.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rentalmobil.adapter.AdapterViewPager
import com.example.rentalmobil.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        view_pager.adapter =
            AdapterViewPager(this)

        TabLayoutMediator(tablayout, view_pager){tab, position ->
            when(position){
                0 -> tab.text = "Home"
                1 -> tab.text = "History"
                2 -> tab.text = "Track"

            }
        }.attach()


    }

}