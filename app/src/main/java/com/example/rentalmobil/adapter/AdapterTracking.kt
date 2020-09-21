package com.example.rentalmobil.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.rentalmobil.R
import com.example.rentalmobil.`class`.Converter
import com.example.rentalmobil.activity.home.DetailTrackingActivity
import com.example.rentalmobil.model.ModelTrackingCar
import kotlinx.android.synthetic.main.list_item_tracking.view.*

class AdapterTracking(
    var context: Context,
    private var list: ArrayList<ModelTrackingCar>
) : RecyclerView.Adapter<AdapterTracking.Holder>() {
    var convert = Converter()

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private var imageCar: ImageView = view.iv_car_item_tracking
        var name: TextView = view.tv_name_car_confirm
        private var customer: TextView = view.tv_plat
        private var order: TextView = view.tv_year
        private var date: TextView = view.tv_color_confirm
        private var constrainLayout: ConstraintLayout = view.cl_car_tracking

        fun convert(tracking: ModelTrackingCar) {


            convert.imageLoader(tracking.url, imageCar,160, 200)

            name.text = tracking.merek
            customer.text = tracking.plat
            order.text = tracking.tahun
            date.text = tracking.warna

            constrainLayout.setOnClickListener {
                val intent = Intent(context.applicationContext, DetailTrackingActivity::class.java)
                intent.putExtra("imageCar", tracking.url)
                intent.putExtra("merek", tracking.merek)
                intent.putExtra("plat", tracking.plat)
                intent.putExtra("tahun", tracking.tahun)
                intent.putExtra("warna", tracking.warna)
                intent.putExtra("kodetracking", tracking.kodetracking)
                context.startActivity(intent)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_tracking, parent, false
            )
        )
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.convert(list[position])

    }

}