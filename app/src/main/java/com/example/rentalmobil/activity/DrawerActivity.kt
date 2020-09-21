package com.example.rentalmobil.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.rentalmobil.R
import kotlinx.android.synthetic.main.activity_drawer.*

class DrawerActivity : AppCompatActivity() {
    private lateinit var toggleButton: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer)

        setUpNavDrawer()

    }

    private fun setUpNavDrawer(){
        toggleButton = ActionBarDrawerToggle(this, drawerLayout,
            R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggleButton)
        toggleButton.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        nav_View.setNavigationItemSelectedListener {
            when(it.itemId){
//                R.id.menu_profile -> Toast.makeText(this, "Profil Checked", Toast.LENGTH_SHORT).show()
                R.id.menu_tentang -> Toast.makeText(this, "Tentang Checked", Toast.LENGTH_SHORT).show()
                R.id.menu_logout -> Toast.makeText(this, "Logout Checked", Toast.LENGTH_SHORT).show()
            }
            true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggleButton.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}