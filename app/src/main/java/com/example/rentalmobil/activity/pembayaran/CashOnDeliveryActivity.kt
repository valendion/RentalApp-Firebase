package com.example.rentalmobil.activity.pembayaran

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rentalmobil.R
import com.example.rentalmobil.activity.BottomNavigationActivity
import kotlinx.android.synthetic.main.activity_cash_on_delivery.*

class CashOnDeliveryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cash_on_delivery)

        btn_ok_cod.setOnClickListener {
            startActivity(Intent(this, BottomNavigationActivity::class.java))
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this, BottomNavigationActivity::class.java))
        super.onBackPressed()
    }
}