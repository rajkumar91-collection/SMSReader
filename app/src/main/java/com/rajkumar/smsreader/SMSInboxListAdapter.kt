package com.rajkumar.smsreader

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rajkumar.smsreader.database.InboxMessageData
import com.rajkumar.smsreader.databinding.RowSmsLayoutBinding

class SMSInboxListAdapter : RecyclerView.Adapter<SMSInboxListAdapter.SMSViewHolder>() {
    private var datas: List<InboxMessageData>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SMSViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowSmsLayoutBinding.inflate(layoutInflater)
        return SMSViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }

    fun setValues(datas: List<InboxMessageData>?) {
        this.datas = datas
        notifyDataSetChanged()
    }



    inner class SMSViewHolder(val binding: RowSmsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data : InboxMessageData) {
           binding.model = data
        }
    }

    override fun onBindViewHolder(holder: SMSViewHolder, position: Int) {
        val smsData = datas?.get(position)
        smsData?.let { holder.bind(it) }
        holder.binding.executePendingBindings()
    }
}