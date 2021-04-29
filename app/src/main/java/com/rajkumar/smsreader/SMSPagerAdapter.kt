package com.rajkumar.smsreader

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter

class SMSPagerAdapter(supportFragmentManager: FragmentManager, behaviour: Int) :
    FragmentPagerAdapter(supportFragmentManager, behaviour) {
    override fun getItem(position: Int): Fragment {
        if (position == 0) {
            return SMSFragment.newInstance(
                "Inbox"
            )
        }
        return SpamSMSFragment.newInstance(
            "Spam"
        )

    }

    override fun getPageTitle(position: Int): CharSequence? {
        if (position == 0) {
            return "Inbox"

        }
        return "Spam"
    }

    override fun getCount(): Int {
        return 2
    }
}