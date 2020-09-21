package com.example.rentalmobil.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.rentalmobil.R
import com.example.rentalmobil.activity.login.LoginActivity
import com.example.rentalmobil.model.ModelUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_bottom_navigation.*

class BottomNavigationActivity : AppCompatActivity() {
    private lateinit var toggleButton: ActionBarDrawerToggle
    var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var appBarCOnfiguration: AppBarConfiguration
    lateinit var data: ModelUser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navigation)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        toggleButton = ActionBarDrawerToggle(
            this, drawerLayoutRental,
            R.string.open, R.string.close
        )

        drawerLayoutRental.addDrawerListener(toggleButton)

        setUpNavDrawer()

        val navControler = findNavController(R.id.fragment_navBot)
        appBarCOnfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.historyFragment, R.id.trackingFragment)
        )


        setupActionBarWithNavController(navControler, appBarCOnfiguration)
        botNavigationView.setupWithNavController(navControler)

        userAndOwner()

    }

    private fun userAndOwner() {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("user")

        myRef.child(mAuth.uid.toString()).addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    var statusPemilik = snapshot.getValue(ModelUser::class.java)!!
                    var status = statusPemilik.status_pemilik ?: ""
                    Log.e("_status","$statusPemilik ${mAuth.uid}")
                    if (status == "No") {
                        botNavigationView.menu.removeItem(R.id.trackingFragment)
                    }
                }

            })


    }

    private fun setUpNavDrawer() {
        drawer_navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_tentang -> startActivity(Intent(this, AboutActivity::class.java))
                R.id.menu_logout -> {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Perhatian !!!")
                    builder.setMessage("Apakah anda ingin keluar ?")

                    builder.setPositiveButton("Ya") { dialog, which ->
                        mAuth.signOut()
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }

                    builder.setNegativeButton("Tidak") { dialog, which ->
                        dialog.dismiss()
                    }
                    builder.show()

                }
            }
            true
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bar, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggleButton.onOptionsItemSelected(item)) {
            return true
        }

        if (item.itemId == R.id.toogleDrawer) {
            drawerLayoutRental.openDrawer(GravityCompat.END)
        }


        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (drawerLayoutRental.isDrawerOpen(GravityCompat.END)) {
            drawerLayoutRental.closeDrawer(GravityCompat.END)
        } else {
            super.onBackPressed()
        }
    }


}