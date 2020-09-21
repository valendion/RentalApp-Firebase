package com.example.rentalmobil.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rentalmobil.R
import kotlinx.android.synthetic.main.activity_view_tracking.*

class ViewTrackingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_tracking)

        val link =
            "<iframe width=\"100%\" height=\"100%\" frameborder=\"0\" src=https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3973.807949980775!" +
                    "2d119.50166231449289!3d-5.1346056533749!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13." +
                    "1!3m3!1m2!1s0x0%3A0x0!2zNcKwMDgnMDQuNiJTIDExOcKwMzAnMTMuOSJF!5e0!3m2!1sid!2sid" +
                    "!4v1599413713501!5m2!1sid!2sid allowfullscreen=\"allowfullscreen\"</iframe>"

        wv_tracking.settings.javaScriptEnabled = true

        wv_tracking.loadData(link, "text/html", "utf-8")
    }
}