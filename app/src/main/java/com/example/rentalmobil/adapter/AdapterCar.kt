package com.example.rentalmobil.adapter

import android.annotation.SuppressLint
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
import com.example.rentalmobil.activity.home.DataPemesananActivity
import com.example.rentalmobil.model.ModelCar
import kotlinx.android.synthetic.main.list_item_car.view.*

class AdapterCar(
    var context: Context,
    var list: ArrayList<ModelCar>,
    var idUser: String?,
    var emailUser: String?
): RecyclerView.Adapter<AdapterCar.Holder>() {
    private var converter = Converter()

    class Holder(view: View): RecyclerView.ViewHolder(view) {
        var nameCar: TextView = view.tv_plat
        var priceCar: TextView = view.tv_name_car_confirm
        var picture: ImageView = view.iv_car_item_tracking
        var itemCar: ConstraintLayout = view.cl_car

        fun convert(mobil: ModelCar){
            val convert = Converter()
            convert.imageLoader(mobil.url,picture,160,200)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_car, parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.nameCar.text = list[position].merek
        holder.priceCar.text = "Rp ${converter.rupiahFormat(list[position].hargadalamkota)} / hari "
        holder.convert(list[position])
        holder.itemCar.setOnClickListener {
           val intent = Intent(context.applicationContext, DataPemesananActivity::class.java)
            intent.putExtra("id", list[position].id)
            intent.putExtra("picture", list[position].url)
            intent.putExtra("title", list[position].merek)
            intent.putExtra("priceInCity", list[position].hargadalamkota)
            intent.putExtra("priceOutCity", list[position].hargaluarkota)
            intent.putExtra("year", list[position].tahun)
            intent.putExtra("color", list[position].warna)
            intent.putExtra("plat", list[position].plat)
            intent.putExtra("fasilitas1", list[position].fasilitas1)
            intent.putExtra("fasilitas2", list[position].fasilitas2)
            intent.putExtra("fasilitas3", list[position].fasilitas3)
            intent.putExtra("fasilitas4", list[position].fasilitas4)
            intent.putExtra("fasilitas5", list[position].fasilitas5)
            intent.putExtra("idUser", idUser)
            intent.putExtra("emailUser", emailUser)
            context.startActivity(intent)
        }

    }
}