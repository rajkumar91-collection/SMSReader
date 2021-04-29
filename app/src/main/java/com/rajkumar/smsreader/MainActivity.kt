package com.rajkumar.smsreader

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import com.google.android.material.tabs.TabLayout
import com.rajkumar.smsreader.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        binding.tblayout.tabGravity = TabLayout.GRAVITY_FILL;


        binding.tblayout.setupWithViewPager(binding.vp)
        val adapter = SMSPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        binding.vp.adapter = adapter

    }

    var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            for (fragment in supportFragmentManager.fragments) {
                if (fragment is SMSFragment) {
                    fragment.onBroadcastReceive()
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        registerReceiver(broadcastReceiver, IntentFilter("NEW_MESSAGE"))
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(broadcastReceiver)
    }
}