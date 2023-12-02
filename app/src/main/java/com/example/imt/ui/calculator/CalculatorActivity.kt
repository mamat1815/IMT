package com.example.imt.ui.calculator

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.imt.data.DataUsers
import com.example.imt.databinding.ActivityCalculatorBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CalculatorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalculatorBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            var sts: String
            var saran: String
            btnCalculate.setOnClickListener {
                val beratBadan = edtBeratBadan.text.toString().trim()
                val tinggiBadan = edtTinggiBadan.text.toString().trim()
                val tinggi = tinggiBadan.toDouble() / 100
                val calculate = beratBadan.toDouble() / (tinggi * tinggi)
                sts = if (calculate < 18.5) {
                    "Berat Badan Kurang (Underweight)"
                } else if (calculate < 22.9) {
                    "Berat Badan Normal"
                } else if (calculate < 24.9) {
                    "Berat Badan Berlebih (Overweight)"
                } else if (calculate < 29.9) {
                    "Obesitas I"
                } else {
                    "Obesitas II"
                }

                saran = if (calculate < 18.5) {
                    "Saran :\n" +
                            "1. Perbanyak waktu istirahat\n" +
                            "2. Perbanyak konsumsi makanan bergizi tinggi\n" +
                            "3. Perbanyak konsumsi protein bukan lemak jahat\n" +
                            "4. Lakukan aktifitas fisik (workout) untuk menjaga kondisi tubuh dalam porses bulking\n" +
                            "5. Konsultasi kepada ahli gizi untuk mendapat pendampingan proses peningkatan berat badan\n" +
                            "\n" +
                            "Resiko :\n" +
                            "1. Malnutrisi (kekurangan nutrisi)\n" +
                            "2. Anemia\n" +
                            "3. Penurunan sistem imun tubuh\n" +
                            "4. Gangguan pertumbuhan dan perkembangan pada anak-anak\n" +
                            "5. Osteoporosis"
                } else if (calculate < 22.9) {
                    "Saran :\n" +
                            "1. Pertahankan pola hidup sehat\n" +
                            "2. Tetap konsumsi makanan bergizi cukup\n" +
                            "3. Tetap penuhi kebutuhan mineral\n" +
                            "4. Rutin lakukan cek IMT untuk mengetahui secara dini perkembangan indeks massa tubuh "
                } else if (calculate < 24.9) {
                    "Saran :\n" +
                            "1. Tingkatkan aktifitas fisik\n" +
                            "2. Kurangi konsumsi garam dan gula\n" +
                            "3. Ubah pola hidup (tidak merokok)\n" +
                            "4. Konsumsi buah untuk menggantikan cemilan\n" +
                            "5. Konsumsi banyak sayur\n" +
                            "6. Cek berkala status IMT untuk deteksi dini obesitas\n" +
                            "\n" +
                            "Resiko :\n" +
                            "1. Jantung Koroner\n" +
                            "2. Stroke\n" +
                            "3. Hipertensi\n" +
                            "4. Gangguan Pernafasan (terutama saat tidur)\n" +
                            "5. Diabetes"
                } else if (calculate < 29.9) {
                    "Saran :\n" +
                            "1. Lakukan diet dan meningkatkan aktifitas fisik dengan komitmen\n" +
                            "2. Batasi konsumsi garam dan gula\n" +
                            "3. Lakukan pola hidup sehat\n" +
                            "4. Batasi istirahat berlebih\n" +
                            "5. Konsumsi makanan sehat (dalam aturan diet)\n" +
                            "6. Konsultasi kepada ahli gizi untuk mendapat pendampingan proses penurunan berat badan\n" +
                            "\n" +
                            "Resiko :\n" +
                            "1. Jantung Koroner\n" +
                            "2. Stroke\n" +
                            "3. Hipertensi\n" +
                            "4. Gangguan Pernafasan (terutama saat tidur)\n" +
                            "5. Diabetes"
                } else {
                    "Saran :\n" +
                            "1. Lakukan diet dan meningkatkan aktifitas fisik dengan komitmen\n" +
                            "2. Batasi konsumsi garam dan gula\n" +
                            "3. Lakukan pola hidup sehat\n" +
                            "4. Batasi istirahat berlebih\n" +
                            "5. Konsumsi makanan sehat (dalam aturan diet)\n" +
                            "6. Konsultasi kepada ahli gizi untuk mendapat pendampingan proses penurunan berat badan\n" +
                            "\n" +
                            "Resiko :\n" +
                            "1. Jantung Koroner\n" +
                            "2. Stroke\n" +
                            "3. Hipertensi\n" +
                            "4. Gangguan Pernafasan (terutama saat tidur)\n" +
                            "5. Diabetes"}
                val dataUser = DataUsers(tinggiBadan,beratBadan,sts,calculate,saran)
               pushData(dataUser)
            }
        }
    }

    private fun pushData(dataUser: DataUsers) {
        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase
            .getInstance(
                    "https://imt-app-178d3-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("IMT")
            .child(auth.currentUser?.uid.toString())
        val dbRef = db.push()
        dbRef.setValue(dataUser).addOnCompleteListener {
            showToast("Data Berhasil dipush")
        }
    }
    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}




