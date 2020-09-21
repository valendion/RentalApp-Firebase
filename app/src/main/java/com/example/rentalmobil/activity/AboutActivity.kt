package com.example.rentalmobil.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rentalmobil.R
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        setSupportActionBar(toolbarAbout)
        supportActionBar?.title = "Tentang"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


    }


}
