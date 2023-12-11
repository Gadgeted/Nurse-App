package com.fire1.nurseapplication.models

data class Nurse_Assignment(
    val allocation_id: Int,
    val flag: String,
    val invoice_no: String,
    val nurse_id: Int,
    val reg_date: String
)