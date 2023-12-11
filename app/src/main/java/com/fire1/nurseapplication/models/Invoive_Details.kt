package com.fire1.nurseapplication.models

data class Invoive_Details(
    val appointment_date: String, //6
    val appointment_time: String, //5
    val book_id: Int, //7
    val booked_for: String, //2
    val dependant_id: Int,
    val invoice_no: String, //1
    val lab_id: Int,
    val latitude: String,
    val longitude: String,
    val member_id: Int,
    val status: String, //4
    val test_id: Int,
    val where_taken: String //3
)