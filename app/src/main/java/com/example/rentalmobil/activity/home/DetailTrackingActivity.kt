package com.example.rentalmobil.activity.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.rentalmobil.R
import com.example.rentalmobil.`class`.Converter
import kotlinx.android.synthetic.main.activity_detail_tracking.*

class DetailTrackingActivity : AppCompatActivity() {
    private val convert = Converter()
    private lateinit var imageCar: String
    private lateinit var nameCar: String
    private lateinit var colorCar: String
    private lateinit var yearCar: String
    private lateinit var platCar: String
    private lateinit var kodeTracking: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tracking)

        imageCar = intent.getStringExtra("imageCar")
        nameCar = intent.getStringExtra("merek")
        colorCar = intent.getStringExtra("warna")
        yearCar = intent.getStringExtra("tahun")
        platCar = intent.getStringExtra("plat")
        kodeTracking = intent.getStringExtra("kodetracking")

        setSupportActionBar(toolbar_tracking)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.title = "Detail Tracking"
        toolbar_tracking.setTitleTextColor(ContextCompat.getColor(this, R.color.colorWhite))

        convert.imageLoader(imageCar,iv_image_car_tracking)
        tv_name_car_confirm.text = nameCar
        tv_plat_tracking.text = platCar
        tv_name_car_confirm.text = nameCar
        tv_color_confirm.text = colorCar
        tv_year_confirm.text = yearCar

        var link = "<iframe src=$kodeTracking" +
                "allowfullscreen=\"allowfullscreen\"" +
                "width=\"100%\" height=\"100%\" frameborder=\"0\" style=\"border:0\"</iframe>"



        val linkMap = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\" \"http://www.w3.org/TR/REC-html40/loose.dtd\">" +
                "<html>" +
                "<body>" +
                "<iframe width=\"100%\" height=\"100%\" frameborder=\"0\" style=\"border:0\"" +
                "src=$kodeTracking" +
                "</body>" +
                "</html>"
        wv_tracking_car.settings.javaScriptEnabled = true
        wv_tracking_car.loadData(linkMap, "text/html", "utf-8")


    }




}
