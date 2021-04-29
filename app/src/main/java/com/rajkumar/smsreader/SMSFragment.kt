package com.rajkumar.smsreader

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.rajkumar.smsreader.database.MessageDatabase
import com.rajkumar.smsreader.databinding.FragmentSMSBinding


private const val SMS_TYPE = "_sms_type"


class SMSFragment : Fragment() {
    private lateinit var adapter: SMSInboxListAdapter
    private var sms_type: String? = null
    private lateinit var binding: FragmentSMSBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            sms_type = it.getString(SMS_TYPE)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_s_m_s, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        activity?.let { it ->
            val db = MessageDatabase.getDatabase(it)
            adapter = SMSInboxListAdapter()
            binding.rvMessages.adapter = adapter
            val decoration = DividerItemDecoration(it, LinearLayout.VERTICAL)
            binding.rvMessages.addItemDecoration(decoration)
            db.messageDao().getInboxMessages().observe(viewLifecycleOwner, Observer {
                if(it!=null)
                    adapter.setValues(it)
            })
        }


    }

    fun onBroadcastReceive() {

        if (isAdded) {

           Log.e("Broadcast","onBroadcastReceive")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(type: String) =
            SMSFragment().apply {
                arguments = Bundle().apply {
                    putString(SMS_TYPE, type)

                }
            }
    }
}