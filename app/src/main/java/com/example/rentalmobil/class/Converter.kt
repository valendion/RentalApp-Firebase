package com.example.rentalmobil.`class`

import android.widget.ImageView
import com.example.rentalmobil.R
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class Converter {

    fun rupiahFormat(number: String): String {
        val rupiahFormat = NumberFormat.getInstance(Locale.GERMANY)
        return rupiahFormat.format(number.toLong())
    }

    fun imageLoader(url: String, imgView: ImageView, widht: Int, height: Int) {
        Picasso.get().load(url)
            .resize(widht, height)
            .error(R.drawable.ic_image)
            .into(imgView)
    }

    fun imageLoader(url: String, imgView: ImageView) {
        Picasso.get().load(url)
            .error(R.drawable.ic_image)
            .into(imgView)
    }

    fun dateFormat(date: String): String {
        val newDate: Date = SimpleDateFormat("yyyy-MM-dd").parse(date)
        val format = SimpleDateFormat("dd/MM/yyyy")
//        newDate = format.parse(date)
        return format.format(newDate)
    }

}