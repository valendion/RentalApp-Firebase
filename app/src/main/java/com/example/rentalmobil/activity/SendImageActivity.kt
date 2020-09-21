package com.example.rentalmobil.activity

import android.app.Activity
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
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rentalmobil.R
import com.example.rentalmobil.model.ModelGambar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_send_image.*

class SendImageActivity : AppCompatActivity() {

    private lateinit var filePathUri: Uri
    private lateinit var storageReferences : StorageReference
    private lateinit var databaseReference: DatabaseReference
    private val imageRequest = 7

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_image)

        storageReferences = FirebaseStorage.getInstance().getReference("Bukti_Pembayaran")

        databaseReference = FirebaseDatabase.getInstance().getReference("Bukti_Pembayaran")

        iv_image.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_DENIED){
                    val permission = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permission, imageRequest)
                }else{
                    pickGalery()
                }
            }else{
                pickGalery()
            }
        }

        btn_send.setOnClickListener {
            uploadImage()
        }


    }

    private fun pickGalery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(Intent.createChooser(intent,"Pilih Gambar"), imageRequest)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == imageRequest){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                pickGalery()
            }else{
                Toast.makeText(this, "Permisi di tolak", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == imageRequest){
            if (data != null && data.data != null){
                filePathUri = data.data!!

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
                    val source = ImageDecoder.createSource(this.contentResolver, filePathUri)
                    val bitmap = ImageDecoder.decodeBitmap(source)
                    iv_image.setImageBitmap(bitmap)
                }else{
                    val firePath = arrayOf(MediaStore.Images.Media.DATA)
                    val cursor: Cursor? = contentResolver.query(filePathUri, firePath, null,null,null)
                    if (cursor != null){
                        cursor.moveToFirst()
                        val kolom = cursor.getColumnIndex(firePath[0])
                        val imagePath = cursor.getString(kolom)

                        iv_image.setImageBitmap(BitmapFactory.decodeFile(imagePath))
                        cursor.close()
                    }


                }
            }
        }

    }

    private fun getFileExtension(uri: Uri): String? {
        val contenResolver: ContentResolver = contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contenResolver.getType(uri))
    }

    private fun uploadImage(){
        if (filePathUri != null){
            val storage: StorageReference = this.storageReferences.child("${System.currentTimeMillis()}.${getFileExtension(filePathUri)}")
            storage.putFile(filePathUri).addOnSuccessListener {
                Toast.makeText(applicationContext, "Image Uploaded Successfully ", Toast.LENGTH_LONG).show()
                val modelGambar = ModelGambar(et_text.text.toString().trim(), it.uploadSessionUri.toString())
                val key = databaseReference.push().key
                databaseReference.child(key!!).setValue(modelGambar)
            }
        }
    }



}