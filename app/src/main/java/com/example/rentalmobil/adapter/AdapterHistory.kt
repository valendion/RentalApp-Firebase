package com.example.rentalmobil.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.rentalmobil.R
import com.example.rentalmobil.`class`.Converter
import com.example.rentalmobil.model.ModelHistoryCar
import kotlinx.android.synthetic.main.list_item_history.view.*

class AdapterHistory(
    var context: Context,
    var list: MutableList<ModelHistoryCar>
): RecyclerView.Adapter<AdapterHistory.Holder>() {
    private val converter = Converter()

    class Holder(view: View): RecyclerView.ViewHolder(view) {
        var name: TextView = view.tv_name_car_history
        var yearAndColor: TextView = view.tv_color_year
        var driver: TextView = view.tv_with_driver
        var area: TextView = view.tv_in_makassar
        var date: TextView = view.tv_tgl_history
        var price: TextView = view.tv_price_car_history
        var status: TextView = view.tv_status_mobil
        var background: ConstraintLayout = view.cl_history
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_history, parent,false
            )
        )
    }

    override fun getItemCount(): Int {
        return this.list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.name.text = list[position].merek
        holder.yearAndColor.text = "${list[position].warna} ${list[position].tahun}"
        holder.driver.text = list[position].opsi_driver
        holder.area.text = list[position].opsi_area
        holder.date.text = list[position].pick_return_date
        holder.price.text = "Rp ${converter.rupiahFormat(list[position].total_harga.toString())}"

        var statusPembayaran = ""
        if (list[position].status_pembayaran == "Confirmed"){
            statusPembayaran = "Pesanan berhasil"
            holder.background.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
        }else if (list[position].status_pembayaran == "Unconfirmed"){
            statusPembayaran = "Pesanan sedang dikonfirmasi"
            holder.background.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWarning))
        }
        holder.status.text = statusPembayaran
    }


}