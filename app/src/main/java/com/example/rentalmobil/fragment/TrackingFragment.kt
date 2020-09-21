package com.example.rentalmobil.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rentalmobil.R
import com.example.rentalmobil.adapter.AdapterTracking
import com.example.rentalmobil.model.ModelTrackingCar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_tracking.*
import kotlinx.android.synthetic.main.fragment_tracking.view.*


class TrackingFragment : Fragment() {

    private val database = FirebaseDatabase.getInstance()
    private val myRef = database.getReference("mobil")
    var dataTracking: ArrayList<ModelTrackingCar> = arrayListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tracking, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rv_tracking.rv_tracking.setHasFixedSize(true)

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataTrack: DataSnapshot in snapshot.children) {
                    val track = dataTrack.getValue(ModelTrackingCar::class.java)
                    dataTracking.add(track!!)
                }
                val trackingAdapter = AdapterTracking(view.context, dataTracking)
                view.rv_tracking.adapter = trackingAdapter
                view.rv_tracking.layoutManager = GridLayoutManager(view.context, 2)


            }

        })





    }


}


