package com.example.rentalmobil.model


data class ModelUser (
    var idUser: String,
    var email: String,
    var password: String,
    var no_hp: String,
    var status_pemilik: String,
    val url_ktp: String
){
    constructor(): this("","","","","","")
}