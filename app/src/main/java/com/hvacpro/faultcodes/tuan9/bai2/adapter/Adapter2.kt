package com.hvacpro.faultcodes.tuan9.bai2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hvacpro.faultcodes.tuan9.databinding.ItemRcvHanBinding

class Adapter2(private val list: MutableList<String>,
               private val onClick: (String)-> Unit,
) : RecyclerView.Adapter<Adapter2.VH>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VH {
        val binding = ItemRcvHanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }
    inner class VH(binding : ItemRcvHanBinding) : RecyclerView.ViewHolder(binding.root){
        val hanTu = binding.tvHanTu
        init {
            binding.root.setOnClickListener {
                val pos = bindingAdapterPosition
                if(pos != RecyclerView.NO_POSITION){
                    onClick(list[pos])
                }
            }
        }
    }
    override fun onBindViewHolder(
        holder: VH,
        position: Int
    ) {
        val hanTu = list[position]
        holder.hanTu.text = hanTu
    }

    override fun getItemCount(): Int {
        return list.size
    }
}