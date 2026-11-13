package com.hvacpro.faultcodes.tuan9.bai1

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hvacpro.faultcodes.tuan9.databinding.ItemRcvBinding
import kotlin.toString

class Adapter(private val ds: MutableList<Note>) : RecyclerView.Adapter<Adapter.VH>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VH {
        val binding = ItemRcvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }
    inner class VH(binding : ItemRcvBinding) : RecyclerView.ViewHolder(binding.root){
        val title = binding.tvTitle
        val content = binding.tvContent
        val date = binding.tvDate
    }

    override fun getItemCount(): Int {
        return ds.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val note = ds[position]
        holder.title.text = note.title
        holder.content.text = note.content
        holder.date.text = note.date
    }

    @SuppressLint("NotifyDataSetChanged")
    fun add(note: Note) {
        ds.add(0 ,note)
        notifyDataSetChanged()
    }
}