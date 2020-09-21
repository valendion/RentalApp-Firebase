package com.example.rentalmobil.activity.home

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.rentalmobil.R
import com.example.rentalmobil.`class`.Converter
import com.example.rentalmobil.activity.login.mAuth
import com.example.rentalmobil.activity.pembayaran.AtmTransferActivity
import com.example.rentalmobil.activity.pembayaran.CashOnDeliveryActivity
import com.example.rentalmobil.model.ModelPesanan
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_confrim.*


class ConfrimActivity : AppCompatActivity() {
    private var driverPrice: Int = 0
    private var totalPayment: Int = 0
    private var rent: Int = 0
    private var convert = Converter()
    private lateinit var toast: Toast
    var id: Long = 0L
    private lateinit var area: String
    private lateinit var driver: String
    private lateinit var datePickReturn: String
    private var nameCar = ""
    var color = ""
    private var year = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confrim)

        setSupportActionBar(toolbar_confrim)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Konfirmasi Pembayaran"
        toolbar_confrim.setTitleTextColor(ContextCompat.getColor(this, R.color.colorWhite))

        id = intent.getLongExtra("id", 0L)
        val imageUrl = intent.getStringExtra("car")
        nameCar = intent.getStringExtra("nameCar")
        color = intent.getStringExtra("color")
        year = intent.getStringExtra("year")
        val pickUp = intent.getStringExtra("datePickUp")
        val dateReturn = intent.getStringExtra("dateReturn")
        val dateDefferend = intent.getLongExtra("dateDeffrend", 0)
        driver = intent.getStringExtra("driver")
        area = intent.getStringExtra("area")
        val priceArea = intent.getIntExtra("priceArea", 0)
        val idUser = intent.getStringExtra("idUser")
        val emailUser = intent.getStringExtra("emailUser")

        if (driver == "Dengan Supir") {
            driverPrice = 100000
        } else {
            driverPrice
        }

        rent = priceArea * dateDefferend.toInt()
        totalPayment = rent + driverPrice
        datePickReturn = "${convert.dateFormat(pickUp)} - ${convert.dateFormat(dateReturn)}"

        tv_name_car_confirm.text = nameCar
        tv_color_confirm.text = "Rp. ${convert.rupiahFormat(priceArea.toString())} / hari"
        tv_year_confirm.text = year
        tv_color_car_confirm.text = color
        tv_date_pickup_confirm.text = datePickReturn
        tv_price_driver.text = "Rp. ${convert.rupiahFormat(driverPrice.toString())}"
        tv_price_rent_confirm.text = "Rp. ${convert.rupiahFormat(rent.toString())}"
        tv_price_total.text = "Rp. ${convert.rupiahFormat(totalPayment.toString())}"

        convert.imageLoader(imageUrl, iv_image_car_confirm)

        btn_atm_transfer.setOnClickListener {
            val intent = Intent(this, AtmTransferActivity::class.java)
            intent.putExtra("id", id)
            intent.putExtra("area", area)
            intent.putExtra("driver", driver)
            intent.putExtra("datePickReturn", datePickReturn)
            intent.putExtra("total", totalPayment)
            intent.putExtra("idUser", idUser)
            intent.putExtra("emailUser", emailUser)
            intent.putExtra("nameCar", nameCar)
            intent.putExtra("year", year)
            intent.putExtra("color", color)
            startActivity(intent)
        }

        btn_cod.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Perhatian!!!")
            builder.setMessage("Data anda sudah benar ?")

            builder.setPositiveButton("Ya") { dialog, which ->
                uploadData()
                startActivity(Intent(this, CashOnDeliveryActivity::class.java))
            }
            builder.setNegativeButton("Tidak") { dialog, which ->
                dialog.dismiss()

            }
            builder.show()

        }

    }

    private fun uploadData() {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("pesanan")

        toast = Toast.makeText(this, "Data Pemesanan terkirim", Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
        val user: FirebaseUser? = mAuth.currentUser
        val key: String = myRef.push().key.toString()
        val modelPesanCod = ModelPesanan(
            key,
            user!!.uid,
            user.email.toString(),
            id_mobil = id,
            opsi_area = area,
            opsi_driver = driver,
            pick_return_date = datePickReturn,
            status_pembayaran = "Unconfirmed",
            status_pengembalian_mobil = "Sedang Digunakan",
            total_harga = totalPayment,
            url_bukti_pembayaran = "",
            merek = nameCar,
            tahun = year,
            warna = color
        )

        myRef.child(key).setValue(modelPesanCod)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }

}