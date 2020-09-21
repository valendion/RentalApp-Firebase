package com.example.rentalmobil.model

data class ModelHistoryCar(
    var id: String,
    var idUser: String,
    var emailUser: String,
    var id_mobil: Long,
    var opsi_area: String,
    var opsi_driver: String,
    var pick_return_date: String,
    var status_pembayaran: String,
    var status_pengembalian_mobil: String,
    var total_harga: Int,
    var url_bukti_pembayaran: String,
    var merek: String,
    var warna: String,
    var tahun: String
){
    constructor():this("","","",0L,"","",""
    ,"","",0,"","","","")
}