package com.rajkumar.smsreader

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rajkumar.smsreader.database.SpamMessageData
import com.rajkumar.smsreader.databinding.RowSpamSmsLayoutBinding

class SMSSpamListAdapter : RecyclerView.Adapter<SMSSpamListAdapter.SMSViewHolder>() {
    private var datas: List<SpamMessageData>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SMSViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowSpamSmsLayoutBinding.inflate(layoutInflater)
        return SMSViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SMSViewHolder, position: Int) {
        val smsData = datas?.get(position)
        smsData?.let { holder.bind(it) }
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }

    fun setValues(datas: List<SpamMessageData>?) {
        this.datas = datas
        notifyDataSetChanged()
    }


    inner class SMSViewHolder(val binding: RowSpamSmsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data : SpamMessageData) {
           binding.model = data
        }
    }


}