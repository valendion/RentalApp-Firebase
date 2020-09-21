package com.example.rentalmobil.activity.pembayaran

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import android.view.MenuItem
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.rentalmobil.R
import com.example.rentalmobil.activity.login.mAuth
import com.example.rentalmobil.model.ModelPesanan
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_confiirm_payment_atm.*

class ConfiirmPaymentAtmActivity : AppCompatActivity() {
    private val codeGalery: Int = 1
    private lateinit var image: Uri
    private lateinit var storageReference: StorageReference
    private lateinit var toast: Toast
    private lateinit var databaseReference: DatabaseReference
    private var idCar: Long = 0L
    private lateinit var area: String
    private lateinit var driver: String
    private lateinit var datePickReturn: String
    lateinit var emailUser: String
    lateinit var idUser: String
    private lateinit var nameCar: String
    private lateinit var year: String
    lateinit var color: String
    private var total: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confiirm_payment_atm)

        setSupportActionBar(toolbar_tracking)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        supportActionBar?.title = "Transfer Atm"
        toolbar_tracking.setTitleTextColor(ContextCompat.getColor(this, R.color.colorWhite))

        storageReference = FirebaseStorage.getInstance().getReference("Bukti_Pembayaran")
        databaseReference = FirebaseDatabase.getInstance().getReference("pesanan")

        idCar = intent.getLongExtra("idCar", 0L)
        area = intent.getStringExtra("area")
        driver = intent.getStringExtra("driver")
        datePickReturn = intent.getStringExtra("datePickReturn")
        emailUser = intent.getStringExtra("emailUser")
        idUser = intent.getStringExtra("idUser")
        total = intent.getIntExtra("total", 0)
        year = intent.getStringExtra("year")
        color = intent.getStringExtra("color")
        nameCar = intent.getStringExtra("nameCar")


        iv_image_proof.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {

                    val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permission, codeGalery)

                } else {
                    pickGalery()
                }
            } else {
                pickGalery()
            }

        }

        btn_proof_transfer.setOnClickListener {
            if (iv_image_proof.drawable.constantState == ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_image,
                    theme
                )?.constantState
            ) {
                toast = Toast.makeText(this, "Masukkan bukti pembayaran", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            } else {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Perhatian!!!")
                builder.setMessage("Data anda sudah benar ?")

                builder.setPositiveButton("Ya") { dialog, which ->
                    UploadImage()
                    startActivity(Intent(this, ThanksActivity::class.java))
                }
                builder.setNegativeButton("Tidak") { dialog, which ->
                    dialog.dismiss()

                }
                builder.show()
            }

        }

    }

    private fun pickGalery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, codeGalery)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == codeGalery) {
            if (grantResults.isNotEmpty() && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED
            ) {
                pickGalery()
            } else {
                toast = Toast.makeText(this, "Permission ditolak", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()

            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == codeGalery) {
            if (data != null && data.data != null) {
                image = data.data!!
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    val source = ImageDecoder.createSource(this.contentResolver, image)
                    val bitmap = ImageDecoder.decodeBitmap(source)
                    iv_image_proof.setImageBitmap(bitmap)
                } else {

                    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)

                    val cursor: Cursor? = contentResolver.query(
                        image, filePathColumn, null,
                        null,
                        null
                    )

                    if (cursor != null) {
                        cursor.moveToFirst()
                        val kolomIndext: Int = cursor.getColumnIndex(filePathColumn[0])
                        val imagePath = cursor.getString(kolomIndext)

                        iv_image_proof.setImageBitmap(BitmapFactory.decodeFile(imagePath))
                        cursor.close()
                    }
                }

            }
        }
    }

    private fun getFIreExtension(uri: Uri): String? {
        val contentResolver: ContentResolver = contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri))
    }

    private fun UploadImage() {
        if (image != null) {
            val storage: StorageReference = this.storageReference
                .child("${System.currentTimeMillis()}.${getFIreExtension(image)}")
            storage.putFile(image).addOnSuccessListener {
                toast = Toast.makeText(this, "Data Pemesanan terkirim", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()

                storage.downloadUrl.addOnSuccessListener {
                    OnSuccessListener<Uri> { TODO("Not yet implemented") }
                    val url = it.toString()
                    val user: FirebaseUser? = mAuth.currentUser
                    val key = databaseReference.push().key
                    val modelPesan = ModelPesanan(
                        key!!, user!!.uid,
                        user.email.toString(), idCar, area, driver, datePickReturn,
                        "Unconfirmed", "Sedang Digunakan", total,
                        url, nameCar, color, year
                    )
                    databaseReference.child(key).setValue(modelPesan)
                }


            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}