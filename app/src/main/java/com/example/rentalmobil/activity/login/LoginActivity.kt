package com.example.rentalmobil.activity.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rentalmobil.R
import com.example.rentalmobil.activity.BottomNavigationActivity
import com.example.rentalmobil.fragment.HomeFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (mAuth.currentUser != null){
            startActivity(Intent(this, BottomNavigationActivity::class.java))
        }
        setContentView(R.layout.activity_login)

        tv_register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        btn_login.setOnClickListener {

            val email = et_email.editText?.text.toString()
            val pass = et_password.editText?.text.toString()

            if (email.isEmpty()) {
                val toast = Toast.makeText(this, "Masukkan email anda", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()

            } else if (!validEmail(email)) {
                val toast = Toast.makeText(this, "Masukkan format dengan benar", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()

            } else if (pass.isEmpty()) {
                val toast = Toast.makeText(this, "Masukkan password anda", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            } else {

                mAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val user: FirebaseUser? = mAuth.currentUser
//                        var idUser: String = user!!.uid
//                        var emailUser: String = user.email.toString()
                            val toast =
                                Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT)
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()
                            val bundle = Bundle()
                            bundle.putString("emailUser", user?.email)
                            bundle.putString("idlUser", user?.uid)
                            val home = HomeFragment()
                            home.arguments = bundle
                            startActivity(Intent(this, BottomNavigationActivity::class.java))
                            finish()
                        } else {
                            val toast = Toast.makeText(this, "Login gagal", Toast.LENGTH_SHORT)
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()
                        }
                    }

            }
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser: FirebaseUser? = mAuth.currentUser
    }

    private fun validEmail(email: String): Boolean{
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }
}