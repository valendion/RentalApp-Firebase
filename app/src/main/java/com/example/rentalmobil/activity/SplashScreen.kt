package com.example.rentalmobil.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.app.ActivityOptionsCompat
import com.example.rentalmobil.R
import com.example.rentalmobil.activity.login.LoginActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            val option = ActivityOptionsCompat.makeSceneTransitionAnimation(this, iv_logo,
                "example_transision"
            )
            startActivity(intent, option.toBundle())

        }, 3000)
    }
}