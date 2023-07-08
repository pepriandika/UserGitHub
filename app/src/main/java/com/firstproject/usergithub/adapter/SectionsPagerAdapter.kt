package com.firstproject.usergithub.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.firstproject.usergithub.view.FollowersFollowingsFragment

class SectionsPagerAdapter(activity: AppCompatActivity, private val login: String) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowersFollowingsFragment()
        fragment.arguments = Bundle().apply {
            putInt("login position", position + 1)
            putString("login", login)
        }
        return fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}