package com.example.rentalmobil.activity.pembayaran

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rentalmobil.R
import com.example.rentalmobil.activity.BottomNavigationActivity
import kotlinx.android.synthetic.main.activity_cash_on_delivery.*

class ThanksActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thanks)

        btn_ok_cod.setOnClickListener {
            startActivity(Intent(this, BottomNavigationActivity::class.java))
        }
    }
}