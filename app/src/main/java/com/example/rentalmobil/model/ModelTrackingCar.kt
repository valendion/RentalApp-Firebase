package com.example.rentalmobil.model

data class ModelTrackingCar(
    var url: String,
    var merek: String,
    var tahun: String,
    var plat: String,
    var kodetracking: String,
    var warna: String,

){
    constructor(): this("","","","","","")
}