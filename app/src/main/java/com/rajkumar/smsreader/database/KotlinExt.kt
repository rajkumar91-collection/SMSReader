package com.rajkumar.smsreader.database

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
@BindingAdapter("time")
fun TextView.bindTime(amount: Long) {
    val sdf = SimpleDateFormat("dd/MM/yy hh:mm a")
    val netDate = Date(amount)
    val date =sdf.format(netDate)
    text = date
}