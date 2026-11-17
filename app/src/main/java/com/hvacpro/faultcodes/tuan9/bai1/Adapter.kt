package com.hvacpro.faultcodes.tuan9.bai1

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hvacpro.faultcodes.tuan9.databinding.ItemRcvBinding
import kotlin.toString

class Adapter(private val list: MutableList<Note>,
              private val onClick: (Note)-> Unit,
              private val onLongClick: (Note, View)-> Unit
    ) : RecyclerView.Adapter<Adapter.VH>(){

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
        init {
            binding.root.setOnClickListener {
                val pos = bindingAdapterPosition
                if(pos != RecyclerView.NO_POSITION){
                    onClick(list[pos])
                }
            }
            binding.root.setOnLongClickListener {
                val pos = bindingAdapterPosition
                if(pos != RecyclerView.NO_POSITION){
                    onLongClick(list[pos], it)
                }
                true
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val note = list[position]
        holder.title.text = note.title
        holder.content.text = note.content
        holder.date.text = Note.formatDate(note.date)
    }
}