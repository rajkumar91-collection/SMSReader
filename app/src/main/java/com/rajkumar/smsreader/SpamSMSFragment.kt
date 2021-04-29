package com.rajkumar.smsreader

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout.VERTICAL
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.rajkumar.smsreader.database.MessageDatabase
import com.rajkumar.smsreader.databinding.FragmentSpamSmsBinding


private const val SMS_TYPE = "_sms_type"


class SpamSMSFragment : Fragment() {
    private lateinit var spamadapter: SMSSpamListAdapter
    private var sms_type: String? = null
    private lateinit var binding: FragmentSpamSmsBinding
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_spam_sms, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        activity?.let { it ->
            val db = MessageDatabase.getDatabase(it)
            spamadapter = SMSSpamListAdapter()
            binding.rvMessages.adapter = spamadapter
            val decoration = DividerItemDecoration(it, VERTICAL)
            binding.rvMessages.addItemDecoration(decoration)
            db.messageDao().getSpamMessages().observe(viewLifecycleOwner, Observer {
                if (it != null)
                    spamadapter.setValues(it)
            })
        }
    }


    fun onBroadcastReceive() {

        if (isAdded) {

            Log.e("Broadcast", "onBroadcastReceive")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(type: String) =
            SpamSMSFragment().apply {
                arguments = Bundle().apply {
                    putString(SMS_TYPE, type)

                }
            }
    }
}