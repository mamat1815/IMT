package com.example.imt.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
data class DataUsers(
    val tinggiBadan: String,
    val beratBadan: String,
    val sts: String,
    val imt: Double,
    val saran: String
) {
    // Tambahkan konstruktor tanpa argumen
    constructor() : this("", "", "", 0.0, "")
}
