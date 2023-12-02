package com.example.imt.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imt.data.DataUsers
import com.example.imt.databinding.ActivityMainBinding
import com.example.imt.ui.calculator.CalculatorActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference
    private lateinit var imtList: ArrayList<DataUsers>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase
            .getInstance(
                "https://imt-app-178d3-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("IMT")
            .child(auth.currentUser?.uid.toString())
        imtList = arrayListOf()
        fetchData()


        binding.apply {
            var x = auth.currentUser?.displayName
            tvWelcome.text = "Selamat Datang $x"
            btnCalculatorIMT.setOnClickListener {
                val intent = Intent(this@MainActivity, CalculatorActivity::class.java)
                startActivity(intent)
            }
            rvImt.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this.context)
            }
        }

    }

    private fun fetchData() {
        db.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                imtList.clear()
                if (snapshot.exists()) {
                    for (imtSnap in snapshot.children) {
                        val imts = imtSnap.getValue(DataUsers::class.java)
                        imtList.add(imts!!)
                    }
                }
                val imtAdapter = IMTAdapter(imtList)
                binding.rvImt.adapter = imtAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}