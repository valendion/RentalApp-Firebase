package com.example.rentalmobil.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rentalmobil.adapter.AdapterCar
import com.example.rentalmobil.R
import com.example.rentalmobil.model.ModelCar
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

lateinit var dataMobil: ArrayList<ModelCar>

class HomeFragment : Fragment() {

    private lateinit var database: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        database = FirebaseDatabase.getInstance().reference
        database.child("mobil").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
                dataMobil = ArrayList()

                for (snapshot: DataSnapshot in p0.children) {
                    val data = snapshot.getValue(ModelCar::class.java)

                    dataMobil.add(data!!)
                }

                view.rv_listCar.setHasFixedSize(true)
                val emailUser = arguments?.getString("emailUser")
                val idUser = arguments?.getString("idlUser")

                Log.e("_home", "$emailUser, $idUser")
                val carAdapter = AdapterCar(
                    view.context,
                    list = dataMobil,
                    emailUser = emailUser,
                    idUser = idUser
                )
                view.rv_listCar.adapter = carAdapter
                view.rv_listCar.layoutManager = GridLayoutManager(view.context, 2)
                pb_loding_home.visibility = View.GONE

            }

        })


        // Inflate the layout for this fragment
        return view
    }


}