package com.example.rentalmobil.activity.pembayaran

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.rentalmobil.R
import com.example.rentalmobil.`class`.Converter
import kotlinx.android.synthetic.main.activity_atm_transfer.*

class AtmTransferActivity : AppCompatActivity() {
private val convert = Converter()
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_atm_transfer)

        setSupportActionBar(toolbar_tracking)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.title = "Transfer Atm"
        toolbar_tracking.setTitleTextColor(ContextCompat.getColor(this, R.color.colorWhite))

        val id = intent.getLongExtra("id",0L)
        val nameCar = intent.getStringExtra("nameCar")
        val year = intent.getStringExtra("year")
        val color = intent.getStringExtra("color")
        val area = intent.getStringExtra("area")
        val driver = intent.getStringExtra("driver")
        val datePickReturn = intent.getStringExtra("datePickReturn")
        val emailUser = intent.getStringExtra("emailUser")
        val idUser = intent.getStringExtra("idUser")
        val total = intent.getIntExtra("total", 0)
        tv_price_bri.text = "Rp ${convert.rupiahFormat(total.toString())}"


        btn_confirm_atm.setOnClickListener {
            val intent = Intent(this, ConfiirmPaymentAtmActivity::class.java)
            intent.putExtra("idCar", id)
            intent.putExtra("area", area)
            intent.putExtra("driver", driver)
            intent.putExtra("datePickReturn", datePickReturn)
            intent.putExtra("emailUser", emailUser)
            intent.putExtra("idUser", idUser)
            intent.putExtra("total", total)
            intent.putExtra("nameCar", nameCar)
            intent.putExtra("year", year)
            intent.putExtra("color", color)
            startActivity(intent)
        }



    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}