package com.example.imt.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imt.data.DataUsers
import com.example.imt.databinding.ItemImtBinding


class IMTAdapter(private val userData: ArrayList<DataUsers>): RecyclerView.Adapter<IMTAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemImtBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemImtBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
       return userData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = userData[position]
        holder.apply {
            binding.apply {

                tvImt.text = "IMT Kamu ${currentItem.imt.toString()}"
                tvBb.text = "Berat Badan Kamu ${currentItem.beratBadan} Kg"
                tvTb.text = "Tinggi Badan Kamu ${currentItem.tinggiBadan} cm"
                tvSts.text = "Kamu Berada Dalam Kondisi ${currentItem.sts}"
                tvSaran.text = "Saran Kami: \n ${currentItem.saran}"
            }
        }
    }
}