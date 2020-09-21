package com.example.rentalmobil.activity.login

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Patterns
import android.view.Gravity
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.rentalmobil.R
import com.example.rentalmobil.model.ModelUser
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {

    private var mAut: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var toast: Toast
    private val codeGalery = 4
    private lateinit var imageKTP: Uri
    private lateinit var source: ImageDecoder.Source
    private lateinit var bitmap: Bitmap
    private lateinit var database: DatabaseReference
    private lateinit var storageReference: StorageReference
    private var email = ""
    private var pass = ""
    private var noTelpon = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        database = FirebaseDatabase.getInstance().getReference("user")
        storageReference = FirebaseStorage.getInstance().getReference("foto_ktp")

        iv_ktp_register.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {
                    val pemision = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(pemision, codeGalery)
                } else {
                    pickGalery()
                }
            } else {
                pickGalery()
            }
        }

        btn_register.setOnClickListener {

            email = et_email_register.editText?.text.toString().trim()
            pass = et_password_register.editText?.text.toString().trim()
            noTelpon = et_noTel_register.editText?.text.toString().trim()

            if (email.isEmpty()) {
                toast = Toast.makeText(this, "Masukkan email anda", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()

            } else if (!validEmail(email)) {
                toast = Toast.makeText(this, "Masukkan format dengan benar", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()

            } else if (pass.isEmpty()) {
                toast = Toast.makeText(this, "Masukkan password anda", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            } else if (pass.length < 6) {
                toast = Toast.makeText(this, "Password terlalu pendek", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            } else if (noTelpon.isEmpty()) {
                toast = Toast.makeText(this, "Masukkan nomor telepon anda", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            } else if (noTelpon.length !in 11..13) {
                toast = Toast.makeText(this, "Nomor telepon salah", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            } else if (iv_ktp_register.drawable.constantState == ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_image,
                    theme
                )?.constantState
            ) {
                toast = Toast.makeText(this, "Masukkan gambar", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            } else {

                mAut.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            if (it.isComplete) {
                                uploadDataUser()
                            }
                            clear()

                        } else {
                            val toast = Toast.makeText(this, "Register gagal", Toast.LENGTH_SHORT)
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()
                        }
                    }

            }


        }

    }

    private fun validEmail(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
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
        when (requestCode) {
            codeGalery -> {
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
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == codeGalery) {
            if (data != null && data.data != null) {
                imageKTP = data.data!!
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    source = ImageDecoder.createSource(this.contentResolver, imageKTP)
                    bitmap = ImageDecoder.decodeBitmap(source)
                    iv_ktp_register.setImageBitmap(bitmap)
                } else {
                    val filePathColomn = arrayOf(MediaStore.Images.Media.DATA)
                    val cursor: Cursor? = contentResolver.query(
                        imageKTP, filePathColomn, null,
                        null,
                        null
                    )

                    if (cursor != null) {
                        cursor.moveToFirst()
                        val kolom = cursor.getColumnIndex(filePathColomn[0])
                        val imagePath = cursor.getString(kolom)
                        iv_ktp_register.setImageBitmap(BitmapFactory.decodeFile(imagePath))
                        cursor.close()
                    }
                }
            }
        }
    }

    private fun getFileExtentision(uri: Uri): String? {
        val contentResolver: ContentResolver = contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri))
    }

    private fun uploadDataUser() {
        if (imageKTP != null) {
            val storge = this.storageReference
                .child("${System.currentTimeMillis()}.${getFileExtentision(imageKTP)}")
            storge.putFile(imageKTP).addOnSuccessListener {


                storge.downloadUrl.addOnSuccessListener {
                    OnSuccessListener<Uri> {}
                    val urlKtp = it.toString()
                    val user = this.mAut.currentUser
                    val keyUser = user?.uid
                    val emailUser = user?.email

                    val userModel = ModelUser(keyUser!!, emailUser!!, pass, noTelpon, "No", urlKtp)
                    database.child(keyUser).setValue(userModel)
                }
            }
        }
    }
    fun clear(){
        et_email_register.editText!!.text.clear()
        et_password_register.editText!!.text.clear()
        et_noTel_register.editText!!.text.clear()
        iv_ktp_register.setImageResource(R.drawable.ic_image)
        toast = Toast.makeText(this, "Register berhasil", Toast.LENGTH_SHORT)
                                toast.setGravity(Gravity.CENTER, 0, 0)
                                toast.show()
    }


}