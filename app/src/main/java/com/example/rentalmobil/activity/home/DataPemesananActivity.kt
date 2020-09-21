package com.example.rentalmobil.activity.home

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import com.example.rentalmobil.R
import com.example.rentalmobil.`class`.Converter
import com.example.rentalmobil.`class`.RangeValidator
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.activity_data_pemesanan.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs


class DataPemesananActivity : AppCompatActivity() {

    private var driver: String = ""
    private var area: String = ""
    private var priceArea = 0
    private var day1 = ""
    private var day2 = ""
    private var dayDefferend = 0L
    private var convert = Converter()
    private var priceInCity = ""
    private var priceOutCity = ""
    private lateinit var toast: Toast
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_pemesanan)

        setSupportActionBar(toolbar_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val url: String = intent.getStringExtra("picture")
        val id: Long = intent.getLongExtra("id", 0L)
        val title = intent.getStringExtra("title")
        priceInCity = intent.getStringExtra("priceInCity")
        priceOutCity = intent.getStringExtra("priceOutCity")
        val color = intent.getStringExtra("color")
        val year = intent.getStringExtra("year")
        val plat = intent.getStringExtra("plat")
        val fasilitas1 = intent.getStringExtra("fasilitas1")
        val fasilitas2 = intent.getStringExtra("fasilitas2")
        val fasilitas3 = intent.getStringExtra("fasilitas3")
        val fasilitas4 = intent.getStringExtra("fasilitas4")
        val fasilitas5 = intent.getStringExtra("fasilitas5")
        val emailUser = intent.getStringExtra("emailUser")?: "Kosong"
        val idUser = intent.getStringExtra("idUser")?: "Kosong"

        convert.imageLoader(url, iv_image_car)


        Log.e("_dataEmail", "$emailUser dan $idUser")
        ct_bar_detail.title = "Data Pemesanan"
        ct_bar_detail.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.colorWhite))
        ct_bar_detail.setExpandedTitleColor(Color.TRANSPARENT)

        tv_price_car_input.text = "Rp ${convert.rupiahFormat(priceInCity)} / hari"
        tv_name_car_input.text = title
        tv_color_car.text = color
        tv_year_car.text = year
        tv_plat_car_input.text = plat

        if (TextUtils.isEmpty(fasilitas1)){
            tv_fuel.visibility = View.GONE
        }else{
            tv_fuel.visibility = View.VISIBLE
        }

        if (TextUtils.isEmpty(fasilitas2)){
            tv_televisition.visibility = View.GONE
        }else{
            tv_televisition.visibility = View.VISIBLE
        }

        if (TextUtils.isEmpty(fasilitas3)){
            tv_Radio.visibility = View.GONE
        }else{
            tv_Radio.visibility = View.VISIBLE
        }

        if (TextUtils.isEmpty(fasilitas4)){
            tv_seat_4.visibility = View.GONE
        }else{
            tv_seat_4.visibility = View.VISIBLE
        }

        if (TextUtils.isEmpty(fasilitas5)){
            tv_seat_6.visibility = View.GONE
        }else{
            tv_seat_6.visibility = View.VISIBLE
        }

        btnRadio(rg_driver, rg_area)


        btn_date.setOnClickListener {
            rangePicker()
        }

        btn_next.setOnClickListener {
            when {
                driver == "" -> {

                    toast = Toast.makeText(this, "Pilih layanan pengemudi", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                }
                area == "" -> {

                    toast = Toast.makeText(this, "Pilih area anda", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                }
                tv_date_pickup.text.isNullOrEmpty() -> {
                    toast = Toast.makeText(this, "Tetapkan jadwal", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                }
                else -> {
                    val intent = Intent(this, ConfrimActivity::class.java)
                    intent.putExtra("car", url)
                    intent.putExtra("nameCar", title)
                    intent.putExtra("id", id)
                    intent.putExtra("color", color)
                    intent.putExtra("year", year)
                    intent.putExtra("datePickUp", day1)
                    intent.putExtra("dateReturn", day2)
                    intent.putExtra("dateDeffrend", dayDefferend)
                    Log.e("_days", dayDefferend.toString())
                    intent.putExtra("driver", driver)
                    intent.putExtra("area", area)
                    intent.putExtra("priceArea", priceArea)
                    intent.putExtra("emailUser", emailUser)
                    intent.putExtra("idUser", idUser)
                    startActivity(intent)
                }
            }
        }
    }

    private fun btnRadio(rg_driver: RadioGroup, rg_area: RadioGroup) {
        rg_driver.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_withDriver -> {
                    rb_withDriver.setTextColor(
                        ContextCompat.getColor(
                            this,
                            R.color.colorWhite
                        )
                    )
                    rb_withoutDriver.setTextColor(
                        ContextCompat.getColor(
                            this,
                            R.color.colorPrimary
                        )
                    )
                    driver = "Dengan Supir"

                }

                R.id.rb_withoutDriver -> {
                    rb_withDriver.setTextColor(
                        ContextCompat.getColor(
                            this,
                            R.color.colorPrimary
                        )
                    )
                    rb_withoutDriver.setTextColor(
                        ContextCompat.getColor(
                            this,
                            R.color.colorWhite
                        )
                    )
                    driver = "Tanpa Supir"
                }

            }
        }

        rg_area.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_makassar -> {
                    rb_makassar.setTextColor(
                        ContextCompat.getColor(
                            this,
                            R.color.colorWhite
                        )
                    )
                    rb_outside.setTextColor(
                        ContextCompat.getColor(
                            this,
                            R.color.colorPrimary
                        )
                    )
                    area = "Makassar"
                    priceArea = priceInCity.toInt()
                }

                R.id.rb_outside -> {
                    rb_makassar.setTextColor(
                        ContextCompat.getColor(
                            this,
                            R.color.colorPrimary
                        )
                    )
                    rb_outside.setTextColor(
                        ContextCompat.getColor(
                            this,
                            R.color.colorWhite
                        )
                    )
                    area = "Di luar Makassar"
                    priceArea = priceOutCity.toInt()
                }

            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun rangePicker() {
        val buildRange = MaterialDatePicker.Builder.dateRangePicker()
        buildRange.setCalendarConstraints(limidRange().build())
        val pickerRange = buildRange.build()
        pickerRange.addOnPositiveButtonClickListener { selection: Pair<Long, Long> ->

            val first = selection.first
            val second = selection.second

            day1 = convertLongToTime(first!!)
            day2 = convertLongToTime(second!!)
            dayDefferend = abs(first - second) / (24 * 60 * 60 * 1000) + 1



            tv_date_pickup.text = "Pickup = ${convert.dateFormat(day1)}"
            tv_return.text = "Return = ${convert.dateFormat(day2)}"
            tv_howMany.text = "$dayDefferend hari"
        }
        pickerRange.show(supportFragmentManager, pickerRange.toString())
    }

    @SuppressLint("SimpleDateFormat")
    private fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(date)
    }

    private fun limidRange(): CalendarConstraints.Builder {
        val constrainBuilder = CalendarConstraints.Builder()

        val calendarStart = Calendar.getInstance()

        Log.e(
            "_bulan",
            "${calendarStart.get(Calendar.YEAR)}, ${calendarStart.get(Calendar.MONTH)}, ${
                calendarStart.get(Calendar.DAY_OF_MONTH) - 1
            }"
        )
        calendarStart.set(
            calendarStart.get(Calendar.YEAR),
            calendarStart.get(Calendar.MONTH),
            calendarStart.get(Calendar.DAY_OF_MONTH) - 1
        )

        val minDate = calendarStart.timeInMillis

        constrainBuilder.setStart(minDate)

        constrainBuilder.setValidator(RangeValidator(minDate))
        return constrainBuilder
    }


}




