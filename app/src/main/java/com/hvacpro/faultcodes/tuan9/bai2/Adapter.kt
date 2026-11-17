package com.hvacpro.faultcodes.tuan9.bai2

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hvacpro.faultcodes.tuan9.bai1.Note
import com.hvacpro.faultcodes.tuan9.bai2.entity.VietTrung
import com.hvacpro.faultcodes.tuan9.databinding.ItemRcv2Binding
import com.hvacpro.faultcodes.tuan9.databinding.ItemRcvBinding
import kotlin.toString

class Adapter(private val list: MutableList<VietTrung>,
              private val onClick: (VietTrung)-> Unit,
    ) : RecyclerView.Adapter<Adapter.VH>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VH {
        val binding = ItemRcv2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }
    inner class VH(binding : ItemRcv2Binding) : RecyclerView.ViewHolder(binding.root){
        val title = binding.tvWord
        val content = binding.tvContent
        init {
            binding.root.setOnClickListener {
                val pos = bindingAdapterPosition
                if(pos != RecyclerView.NO_POSITION){
                    onClick(list[pos])
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val vietTrung = list[position]
        holder.title.text = vietTrung.word
        holder.content.text = vietTrung.content
    }

}